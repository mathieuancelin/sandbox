package com.serli.soap.scope;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class SOAPRequestContext {

    /**
     * Callbacks appelés à la destruction du scope.
     */
    private final Map<String, Runnable> requestDestructionCallbacks = new LinkedHashMap<String, Runnable>(8);
    /**
     * Beans managés pendant la vie du scope.
     */
    private final Map<String, Object> contextualizedBeans = new HashMap<String, Object>();
    /**
     * Flag permettant de savoir si la requete est en vie.
     */
    private volatile boolean requestActive = true;

    /**
     * Récupère un bean géré par le scope en cours.
     * 
     * @param clazz
     *            classe du bean.
     * @return instance de {@code clazz}.
     */
    public Object getBean(String clazz) {
        synchronized (this.contextualizedBeans) {
            return contextualizedBeans.get(clazz);
        }
    }

    /**
     * Set d'un bean géré par le scope en cours.
     * 
     * @param clazz
     *            classe du bean.
     * @param value
     *            valeur du bean pour le scope.
     */
    public void setBean(String clazz, Object value) {
        synchronized (this.contextualizedBeans) {
            contextualizedBeans.put(clazz, value);
        }
    }

    /**
     * Retrait d'un bean managé par le scope.
     * 
     * @param clazz
     *            classe du bean à retirer.
     */
    public void removeBean(String clazz) {
        synchronized (this.contextualizedBeans) {
            contextualizedBeans.remove(clazz);
        }
    }

    /**
     * Méthode appelée à la fin d'une requete SOAP pour nettoyer le context du
     * scope.
     */
    public void requestCompleted() {
        executeRequestDestructionCallbacks();
        this.requestActive = false;
    }

    /**
     * @return l'état de la requete.
     */
    public final boolean isRequestActive() {
        return this.requestActive;
    }

    /**
     * Ajout d'un callback de destruction.
     * 
     * @param name
     *            nom du callback.
     * @param callback
     *            le callback de destruction.
     */
    public final void registerRequestDestructionCallback(String name, Runnable callback) {
        if (name == null)
            throw new IllegalStateException("Name must not be null");
        if (callback == null)
            throw new IllegalStateException("Callback must not be null");
        synchronized (this.requestDestructionCallbacks) {
            this.requestDestructionCallbacks.put(name, callback);
        }
    }

    /**
     * Retrait d'un callback de destruction.
     * 
     * @param name
     *            nom du callback à retirer.
     */
    public final void removeRequestDestructionCallback(String name) {
        if (name == null)
            throw new IllegalStateException("Name must not be null");
        synchronized (this.requestDestructionCallbacks) {
            this.requestDestructionCallbacks.remove(name);
        }
    }

    /**
     * Execution des callbacks de destruction.
     */
    private void executeRequestDestructionCallbacks() {
        synchronized (this.requestDestructionCallbacks) {
            for (Runnable runnable : this.requestDestructionCallbacks.values()) {
                runnable.run();
            }
            this.requestDestructionCallbacks.clear();
        }
    }
}
