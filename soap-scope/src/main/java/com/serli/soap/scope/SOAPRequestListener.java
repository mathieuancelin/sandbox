package com.serli.soap.scope;

import javax.inject.Singleton;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPMessageContext;

public class SOAPRequestListener implements Handler<SOAPMessageContext> {

    /**
     * Initalisation du contexte soap pour le scope soap.
     */
    public final void incomingRequest() {
        // TODO récuperation des attributs saop si necessaire
    }

    /**
     * Nettoyage du contexte du scope soap.
     */
    public final void outgoingRequest() {
        final SOAPRequestContext context = SOAPRequestContextHolder.getRequestContext();
        if (context != null) {
            context.requestCompleted();
            SOAPRequestContextHolder.resetRequestContext();
        }
    }

    @Override
    public final boolean handleMessage(final SOAPMessageContext context) {
        final Boolean outboundProperty = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        if (outboundProperty) {
            outgoingRequest();
        } else {
            incomingRequest();
        }
        return true;
    }

    @Override
    public final boolean handleFault(final SOAPMessageContext context) {
        final Boolean outboundProperty = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        if (outboundProperty) {
            outgoingRequest();
        } else {
            incomingRequest();
        }
        return true;
    }

    @Override
    public final void close(MessageContext mc) {
        // rien a faire ici
    }
}
