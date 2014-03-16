package com.evilco.event.handler;

import com.evilco.event.IEvent;

import java.lang.reflect.Method;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class MethodHandler implements IHandler {

	/**
	 * Defines whether the handler will accept cancelled events.
	 */
	protected final boolean acceptsCancelledEvents;

	/**
	 * Defines the handler method.
	 */
	protected final Method method;

	/**
	 * Defines the handler object.
	 */
	protected final Object object;

	/**
	 * Defines the handler priorty.
	 */
	protected final HandlerPriority priority;

	/**
	 * Constructs a new MethodHandler.
	 * @param object
	 * @param method
	 * @param priority
	 */
	public MethodHandler (Object object, Method method, boolean acceptsCancelledEvents, HandlerPriority priority) {
		this.object = object;
		this.method = method;
		this.acceptsCancelledEvents = acceptsCancelledEvents;
		this.priority = priority;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean acceptsCancelledEvents () {
		return this.acceptsCancelledEvents;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HandlerPriority getPriority () {
		return this.priority;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handle (IEvent event) throws Exception {
		this.method.invoke (this.object, event);
	}
}
