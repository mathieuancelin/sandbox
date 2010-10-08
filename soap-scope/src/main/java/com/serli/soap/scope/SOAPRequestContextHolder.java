package com.serli.soap.scope;

public final class SOAPRequestContextHolder {

    /**
     * Instance singleton / thread du contexte soap.
     */
    private static final ThreadLocal<SOAPRequestContext> REQUEST_CONTEXT_HOLDER = new ThreadLocal<SOAPRequestContext>() {
        @Override
        protected SOAPRequestContext initialValue() {
            return new SOAPRequestContext();
        }
    };

    /**
     * Constructeur privé.
     */
    private SOAPRequestContextHolder() {
        // on cache le contructeur.
    }

    /**
     * Reset du threadlocal.
     */
    public static void resetRequestContext() {
        REQUEST_CONTEXT_HOLDER.remove();
    }

    /**
     * Set du singleton / thread de context soap.
     * 
     * @param context
     *            le contexte soap courant.
     */
    public static void setRequestContext(SOAPRequestContext context) {
        REQUEST_CONTEXT_HOLDER.set(context);
    }

    /**
     * Récupération du threadlocal de soap context.
     * 
     * @return l'instance de contexte du thread en cours.
     */
    public static SOAPRequestContext getRequestContext() {
        return REQUEST_CONTEXT_HOLDER.get();
    }
}
