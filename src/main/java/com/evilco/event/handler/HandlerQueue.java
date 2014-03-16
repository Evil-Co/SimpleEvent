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
package com.evilco.event.handler;

import com.evilco.event.ICancellableEvent;
import com.evilco.event.IEvent;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class HandlerQueue extends PriorityQueue<IHandler> {

	/**
	 * Constructs a new HandlerQueue instance.
	 */
	public HandlerQueue () {
		super (1, new HandlerComparator ());
	}

	/**
	 * Fires an event.
	 * @param event
	 */
	public void fire (IEvent event) throws Exception {
		for (IHandler handler : this) {
			// skip cancelled events
			if (event instanceof ICancellableEvent && ((ICancellableEvent) event).isCancelled () && ((ICancellableEvent) event).isCancelled () && !handler.acceptsCancelledEvents ()) continue;

			// call handler
			handler.handle (event);
		}
	}

	/**
	 * Compares handlers.
	 */
	public static class HandlerComparator implements Comparator<IHandler> {

		/**
		 * {@inheritDoc}
		 */
		@Override
		public int compare (IHandler o1, IHandler o2) {
			if (o1.getPriority ().value < o2.getPriority ().value) return 1;
			if (o1.getPriority ().value > o2.getPriority ().value) return -1;
			return 0;
		}
	}
}
