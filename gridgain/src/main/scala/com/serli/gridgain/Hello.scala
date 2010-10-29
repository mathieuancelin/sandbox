package com.serli.gridgain

import org.gridgain.scalar.scalar
import scalar._
import java.util.concurrent.CountDownLatch
import org.gridgain.grid.GridListenActor
import java.util.UUID

object Hello {
    def main(args: Array[String]) {
        
        scalar {
            // broadcast closure on every nodes
            grid !!! (() => println("Hello World !!!"))

            // spread closures on available nodes
            grid !!~ (for (w <- "Hello Scala World !!!".split(" "))
                                  yield () => println(w))

            // map reduce count on nodes
            println("Count of non-whitespace is: "
                    + count("Scala is so cool and very complicated!"))

            // conversation between 2 nodes
            if (grid.nodes().size < 2) {
                error("I need a partner to play a ping pong!")
                return
            } else {
                pingPong
            }
        }
    }

    /**
     * Map reduce string characters count.
     */
    def count(msg: String) =
        grid !*~ (for (w <- msg.split(" ")) yield () => {
            println("calculating for: " + w)
            w.length
        },
        (s: Seq[Int]) => s.reduceLeft(_ + _))

    /**
     * Messages between 2 nodes    .
     */
    def pingPong() {

        val loc = grid.localNode
        val rmt = grid.remoteNodes().iterator.next

        rmt.remoteListenAsync(loc, new GridListenActor[String]() {
            var count = 0;
            def receive(nodeId: UUID, msg: String) {
                count = count +1
                println(count + " => " + msg)
                msg match {
                    case "PING" => respond("PONG")
                    case "STOP" => stop
                }
            }
        }).get

        val MAX_PLAYS = 100

        val cnt = new CountDownLatch(MAX_PLAYS)

        rmt.listen(new GridListenActor[String]() {
            var count = 0;
            def receive(nodeId: UUID, msg: String) {
                count = count +1
                println(count + " => " + msg)
                if (cnt.getCount() == 1) {
                    stop("STOP")
                } else {
                    msg match {
                        case "PONG" => respond("PING")
                    }
                }
                cnt.countDown();
            }
        })
        rmt !< "PING"
        cnt.await()
    }
}

/*
                       Operators:
    ---------------------------------------------------
    |  !<  - message sending                          |
    |  !!! - broadcast(!) closure {call|run}(!)       |
    |  !!< - unicast(<) closure {call|run}(!)         |
    |  !!~ - spread(~) closure {call|run}(!)          |
    |  !!^ - load balance(^) closure {call|run}(!)    |
    |                                                 |
    |  !*! - reduce(*) closure via broadcast(!)       |
    |  !*< - reduce(*) closure via unicast(<)         |
    |  !*~ - reduce(*) closure via spread(~)          |
    |  !*^ - reduce(*) closure via load balancing(^)  |
    |                                                 |
    |  ~~  - projection(~) creation(~)                |
    |  ~+  - projections(~) merge(+)                  |
    |  ~*  - projection(~) crossing(*)                |
    ---------------------------------------------------
*/