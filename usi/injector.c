#include <arpa/inet.h>
#include <assert.h>
#include <errno.h>
#include <netinet/in.h>
#include <signal.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/wait.h>
#include <netdb.h>
#include <unistd.h>

#define SA      struct sockaddr
#define MAXLINE 4096
#define MAXSUB  400


#define LISTENQ         1024

extern int h_errno;

ssize_t process_http(int sockfd, char *host, char *page, char *poststr) {
	char sendline[MAXLINE + 1], recvline[MAXLINE + 1];
	ssize_t n;
	snprintf(sendline, MAXSUB,
		 "POST %s HTTP/1.0\r\n"
		 "Host: %s\r\n"
		 "Content-type: application/json\r\n"
		 "Content-length: %d\r\n\r\n"
		 "%s", page, host, strlen(poststr), poststr);
	write(sockfd, sendline, strlen(sendline));
	while ((n = read(sockfd, recvline, MAXLINE)) > 0) {

		recvline[n] = '\0';
		//printf("%s", recvline);
	}
	return n;
}

int main(void) {
	int sockfd;
	struct sockaddr_in servaddr;

	char **pptr;
	//********** You can change. Puy any values here *******
	char *hname = "soap-bubble";
	char *page = "/api/user";
	char *poststr = "{firstname:'maurice',lastname:'plutanfiard',mail:'maurice.plutanfiard@gmail.com',password:'xxxxx'}\r\n";
	//*******************************************************
	char str[50];
	char url[MAXLINE + 1];
	struct hostent *hptr;
	if ((hptr = gethostbyname(hname)) == NULL) {
		fprintf(stderr, " gethostbyname error for host: %s: %s",
			hname, hstrerror(h_errno));
		exit(1);
	}
	printf("hostname: %s\n", hptr->h_name);
	if (hptr->h_addrtype == AF_INET
	    && (pptr = hptr->h_addr_list) != NULL) {
		printf("address: %s\n",
		       inet_ntop(hptr->h_addrtype, *pptr, str,
				 sizeof(str)));
	} else {
		fprintf(stderr, "Error call inet_ntop \n");
	}
	int i = 0, count = 510000;
	for (i=500001; i<1000000; i++) {
		sockfd = socket(AF_INET, SOCK_STREAM, 0);
		bzero(&servaddr, sizeof(servaddr));
		servaddr.sin_family = AF_INET;
		servaddr.sin_port = htons(8080);
		inet_pton(AF_INET, str, &servaddr.sin_addr);
		connect(sockfd, (SA *) & servaddr, sizeof(servaddr));
		if (i == count) {
			printf("call : %d\n", i);
			count = count + 10000;
		}
		sprintf(url, "{firstname:'maurice',lastname:'plutanfiard',mail:'maurice.plutanfiard%i@gmail.com',password:'xxxxx'}\r\n", i);
		//printf("%s", url);
		process_http(sockfd, hname, page, url);
		close(sockfd);
	}
	exit(0);
}
