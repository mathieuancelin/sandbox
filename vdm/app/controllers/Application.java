package controllers;

import javax.inject.Inject;
import play.mvc.*;
import services.RandomService;

public class Application extends Controller {

    @Inject
    private static RandomService service;

    public static void index() {
		String wsResponse = service.getRandomVDMs(3);
        render(wsResponse);
    }
}