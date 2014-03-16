package com.evilco.event.handler;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public enum HandlerPriority {
	MONITOR (3),
	HIGHEST (2),
	HIGH (1),
	NORMAL (0),
	LOW (-1),
	LOWEST (-2);

	/**
	 * Defines the priority value.
	 */
	public final int value;

	/**
	 * Constructs a new HandlerPriority.
	 * @param value
	 */
	private HandlerPriority (int value) {
		this.value = value;
	}
}
