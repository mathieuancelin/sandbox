package controllers;

import javax.inject.Inject;
import play.mvc.*;
import services.RandomService;
import binders.VDM;

public class Application extends Controller {

    @Inject @VDM
    private static RandomService service;

    public static void index() {
		String wsResponse = service.getRandomVDMs(3);
        render(wsResponse);
    }
}