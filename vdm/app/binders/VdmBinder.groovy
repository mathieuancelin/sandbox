bindings {

    bind services.RandomService.class, 
		annotatedWith: VDM.class, 
		to: services.impl.RandomServiceImpl.class
    
}