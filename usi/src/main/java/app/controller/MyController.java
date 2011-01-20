/*
 *  Copyright 2010 mathieuancelin.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */

package app.controller;

import app.model.User;
import app.model.Users;
import cx.ath.mancel01.webframework.view.View;
import cx.ath.mancel01.webframework.annotation.Controller;
import cx.ath.mancel01.webframework.data.DataHelper;
import cx.ath.mancel01.webframework.view.Render;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Controller
public class MyController {

    @Inject
    private Users users;

    @Path("/")
    public View index() {
        return new View();
    }

    @Path("/count")
    public void count() {
        Render.text(String.valueOf(DataHelper.forType(User.class).count())).go();
    }

    @Path("/api/user")
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public void addUser(User u) {
        if (users.add(u)) {
            Render.ok().go();
        }
        Render.badRequest().go();
    }
}
