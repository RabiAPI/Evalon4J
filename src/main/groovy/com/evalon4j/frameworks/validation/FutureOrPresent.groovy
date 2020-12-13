package com.evalon4j.frameworks.validation
/**
 * The annotated element must be an instant, date or time in the present or in the future.
 * <p>
 * <i>Now</i> is defined by the {@link ClockProvider} attached to the {@link Validator} or
 * {@link ValidatorFactory}. The default {@code clockProvider} defines the current time
 * according to the virtual machine, applying the current default time zone if needed.
 * <p>
 * The notion of present here is defined relatively to the type on which the constraint is
 * used. For instance, if the constraint is on a {@link java.time.Year}, present would mean the whole
 * current year.
 * <p>
 * Supported types are:
 * <ul>
 *     <li>{@code java.util.Date}</li>
 *     <li>{@code java.util.Calendar}</li>
 *     <li>{@code java.time.Instant}</li>
 *     <li>{@code java.time.LocalDate}</li>
 *     <li>{@code java.time.LocalDateTime}</li>
 *     <li>{@code java.time.LocalTime}</li>
 *     <li>{@code java.time.MonthDay}</li>
 *     <li>{@code java.time.OffsetDateTime}</li>
 *     <li>{@code java.time.OffsetTime}</li>
 *     <li>{@code java.time.Year}</li>
 *     <li>{@code java.time.YearMonth}</li>
 *     <li>{@code java.time.ZonedDateTime}</li>
 *     <li>{@code java.time.chrono.HijrahDate}</li>
 *     <li>{@code java.time.chrono.JapaneseDate}</li>
 *     <li>{@code java.time.chrono.MinguoDate}</li>
 *     <li>{@code java.time.chrono.ThaiBuddhistDate}</li>
 * </ul>
 * <p>
 * {@code null} elements are considered valid.
 *
 * @author Guillaume Smet
 * @since 2.0
 */
class FutureOrPresent extends JavaValidationAnnotation {
    String annotationName = "@FutureOrPresent"
}