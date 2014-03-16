import com.evilco.event.EventManager;
import com.evilco.event.ICancellableEvent;
import com.evilco.event.IEvent;
import com.evilco.event.annotation.EventHandler;
import com.evilco.event.handler.HandlerPriority;
import com.evilco.event.handler.IHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.reflect.InvocationTargetException;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
@RunWith (MockitoJUnitRunner.class)
public class BasicTest {

	/**
	 * Tests annotation based handler registrations.
	 * @throws Exception
	 */
	@Test (expected = InvocationTargetException.class)
	public void annotationRegistrationTest () throws Exception {
		// create event manager
		EventManager eventManager = new EventManager ();

		// register handler
		eventManager.registerHandler (new AnnotationExceptionHandler ());

		// fire event
		eventManager.fireEvent (new TestEvent ());
	}

	/**
	 * Tests manual test registrations.
	 */
	@Test (expected = TestException.class)
	public void handlerRegistrationTest () throws Exception {
		// create event manager
		EventManager eventManager = new EventManager ();

		// register handler
		eventManager.registerHandler (TestEvent.class, new ExceptionTestEventHandler (HandlerPriority.NORMAL));

		// fire event
		eventManager.fireEvent (new TestEvent ());
	}

	/**
	 * Tests handler priorities.
	 * @throws Exception
	 */
	@Test
	public void cancelPriorityTest () throws Exception {
		// create event manager
		EventManager eventManager = new EventManager ();

		// register handlers
		eventManager.registerHandler (TestEvent.class, new ExceptionTestEventHandler (HandlerPriority.LOWEST));
		eventManager.registerHandler (TestEvent.class, new CancelTestEventHandler (HandlerPriority.LOW));

		// fire event
		eventManager.fireEvent (new TestEvent ());
	}

	/**
	 * Defines a test event.
	 */
	public static class TestEvent implements IEvent, ICancellableEvent {

		/**
		 * Indicates whether the event has been cancelled.
		 */
		protected boolean cancelled = false;

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean isCancelled () {
			return this.cancelled;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void setCancelled (boolean value) {
			this.cancelled = value;
		}
	}

	/**
	 * Defines a test event handler.
	 */
	public static class TestEventHandler implements IHandler {

		/**
		 * Stores the handler priority.
		 */
		protected final HandlerPriority priority;

		/**
		 * Constructs a new TestEventHandler.
		 * @param priority
		 */
		public TestEventHandler (HandlerPriority priority) {
			this.priority = priority;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean acceptsCancelledEvents () {
			return false;
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
		public void handle (IEvent event) throws Exception { }
	}

	/**
	 * Defines a test event handler which cancels all events handled.
	 */
	public static class CancelTestEventHandler extends TestEventHandler {

		/**
		 * Constructs a new CancelTestEventHandler.
		 * @param priority
		 */
		public CancelTestEventHandler (HandlerPriority priority) {
			super (priority);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void handle (IEvent event) throws Exception {
			((ICancellableEvent) event).setCancelled (true);
		}
	}

	/**
	 * Defines a test event handler.
	 */
	public static class ExceptionTestEventHandler extends TestEventHandler {

		/**
		 * Constructs a new ExceptionTestEventHandler.
		 * @param priority
		 */
		public ExceptionTestEventHandler (HandlerPriority priority) {
			super (priority);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void handle (IEvent event) throws Exception {
			throw new TestException ();
		}
	}

	/**
	 * Defines a test handler (annotation based).
	 */
	public static class AnnotationExceptionHandler {

		/**
		 * Handles a TestEvent.
		 * @param event
		 */
		@EventHandler
		public void handler (TestEvent event) throws TestException {
			throw new TestException ();
		}
	}

	/**
	 * Defines a test exception.
	 */
	public static class TestException extends Exception {

		public TestException () {
			super ();
		}

		public TestException (String message) {
			super (message);
		}

		public TestException (String message, Throwable cause) {
			super (message, cause);
		}

		public TestException (Throwable cause) {
			super (cause);
		}

		protected TestException (String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
			super (message, cause, enableSuppression, writableStackTrace);
		}
	}
}
