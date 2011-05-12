package cx.ath.mancel01.restmvc.view;

public interface ParameterizedRender {

    void go();

    ParameterizedRender param(String name, Object value);

}
