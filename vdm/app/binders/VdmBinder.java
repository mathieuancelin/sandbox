package binders;

import cx.ath.mancel01.dependencyshot.graph.Binder;
import services.RandomService;
import services.impl.RandomServiceImpl;

public class VdmBinder extends Binder {

    @Override
    public void configureBindings() {
        bind(RandomService.class).to(RandomServiceImpl.class);
    }

}
