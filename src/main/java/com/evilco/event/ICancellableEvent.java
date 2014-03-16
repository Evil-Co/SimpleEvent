package com.evilco.event;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public interface ICancellableEvent {

	/**
	 * Indicates whether the event has been cancelled or not.
	 * @return
	 */
	public boolean isCancelled ();

	/**
	 * Sets whether the event has been cancelled or not.
	 * @param value
	 */
	public void setCancelled (boolean value);
}
