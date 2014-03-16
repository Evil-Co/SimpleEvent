/**
 * This file is part of the Simple Event Project.
 *
 * The Simple Event Project is free software: you can redistribute it and/or modify
 * it under the terms of the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.evilco.event;

import com.evilco.event.annotation.EventHandler;
import com.evilco.event.handler.HandlerPriority;
import com.evilco.event.handler.HandlerQueue;
import com.evilco.event.handler.IHandler;
import com.evilco.event.handler.MethodHandler;
import com.google.common.base.Preconditions;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class EventManager {

	/**
	 * Stores a map of registered handlers.
	 */
	protected Map<Class<? extends IEvent>, HandlerQueue> handlerMap;

	/**
	 * Constructs a new EventManager.
	 */
	public EventManager () {
		this.handlerMap = new HashMap<> ();
	}

	/**
	 * Builds a new handler queue for the specified event.
	 * @param event
	 * @return
	 */
	public HandlerQueue buildHandlerQueue (Class<? extends IEvent> event) {
		// create handler queue
		HandlerQueue queue = new HandlerQueue ();

		// append queues
		if (this.handlerMap.containsKey (event)) queue.addAll (this.handlerMap.get (event));

		// search for child
		if (event.getSuperclass () != null) {
			Class<?> superEvent = event.getSuperclass ();

			// cast
			try {
				queue.addAll (this.buildHandlerQueue (superEvent.asSubclass (IEvent.class)));
			} catch (ClassCastException ex) { }
		}

		// return finished queue
		return queue;
	}

	/**
	 * Returns (or creates) the handler list for the specified event.
	 * @param event
	 * @return
	 */
	public HandlerQueue getHandlerList (Class<? extends IEvent> event) {
		if (!this.handlerMap.containsKey (event)) this.handlerMap.put (event, new HandlerQueue ());
		return this.handlerMap.get (event);
	}

	/**
	 * Fires an event.
	 * @param event
	 * @throws Exception
	 */
	public void fireEvent (IEvent event) throws Exception {
		// build handler queue
		HandlerQueue queue = this.buildHandlerQueue (event.getClass ());

		// execute queue
		queue.fire (event);
	}

	/**
	 * Registers a new handler instance.
	 * @param event
	 * @param handler
	 */
	public void registerHandler (Class<? extends IEvent> event, IHandler handler) {
		this.getHandlerList (event).add (handler);
	}

	/**
	 * Registers a new handler instance.
	 * @param event
	 * @param object
	 * @param method
	 * @param acceptsCancelledEvents
	 * @param priority
	 */
	public void registerHandler (Class<? extends IEvent> event, Object object, Method method, boolean acceptsCancelledEvents, HandlerPriority priority) {
		this.registerHandler (event, new MethodHandler (object, method, acceptsCancelledEvents, priority));
	}

	/**
	 * Registers a new handler.
	 * @param object
	 */
	public void registerHandler (Object object) {
		// verify attributes
		Preconditions.checkNotNull (object, "object");

		// iterate over methods
		for (Method method : object.getClass ().getMethods ()) {
			// skip non-event handler methods
			if (!method.isAnnotationPresent (EventHandler.class)) continue;

			// get arguments
			Class<?>[] arguments = method.getParameterTypes ();

			// verify method arguments
			Preconditions.checkState (arguments.length == 1, "Event handler methods cannot accept more than one arguments");
			Preconditions.checkState (IEvent.class.isAssignableFrom (arguments[0]), "Event handler methods must accept an instance of IEvent as their first parameter");

			// register handler
			this.registerHandler (arguments[0].asSubclass (IEvent.class), object, method, method.getAnnotation (EventHandler.class).ignoreCancelled (), method.getAnnotation (EventHandler.class).priority ());
		}
	}
}