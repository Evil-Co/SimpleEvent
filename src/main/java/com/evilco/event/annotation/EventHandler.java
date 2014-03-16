package com.evilco.event.annotation;

import com.evilco.event.handler.HandlerPriority;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
@Retention (RetentionPolicy.RUNTIME)
@Target (ElementType.METHOD)
public @interface EventHandler {

	/**
	 * Defines whether the handler receives already cancelled events.
	 * @return
	 */
	public boolean ignoreCancelled () default false;

	/**
	 * Defines the handler priority.
	 * @return
	 */
	public HandlerPriority priority () default HandlerPriority.NORMAL;
}
