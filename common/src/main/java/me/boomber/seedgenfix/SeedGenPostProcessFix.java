package me.boomber.seedgenfix;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.filter.AbstractFilter;

/**
 * Suppresses the vanilla base {@code ChunkAccess.markPosForPostProcessing}
 * warning that SeedGen triggers from its stand-in chunks.
 *
 * The full body of that method is a single {@code LOGGER.warn(...)} call with
 * no side effects, which is why filtering the message is equivalent to
 * cancelling the method. Real chunks override it, so nothing is lost.
 *
 * The filter is attached to every {@link LoggerConfig} (and the root config)
 * because Log4j only consults filters on logger configs and appenders, not on
 * the {@link Configuration} itself. Doing it there keeps this loader- and
 * Minecraft-version-independent.
 */
public final class SeedGenPostProcessFix {

	private static final String MESSAGE = "Trying to mark a block for post processing";

	private SeedGenPostProcessFix() {
	}

	public static void init() {
		try {
			LoggerContext context = (LoggerContext) LogManager.getContext(false);
			Configuration configuration = context.getConfiguration();
			Filter filter = new AbstractFilter() {
				@Override
				public Result filter(LogEvent event) {
					try {
						if (event.getMessage() != null
								&& event.getMessage().getFormattedMessage().contains(MESSAGE)) {
							return Result.DENY;
						}
					} catch (Throwable ignored) {
						// Fall through to NEUTRAL.
					}
					return Result.NEUTRAL;
				}
			};

			for (LoggerConfig loggerConfig : configuration.getLoggers().values()) {
				loggerConfig.addFilter(filter);
			}
			configuration.getRootLogger().addFilter(filter);
		} catch (Throwable ignored) {
			// A log filter must never break game startup.
		}
	}
}
