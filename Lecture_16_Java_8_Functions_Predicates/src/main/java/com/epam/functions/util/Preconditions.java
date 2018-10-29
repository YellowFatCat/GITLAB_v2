package com.epam.functions.util;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;
import java.io.File;
import java.time.Instant;
import java.util.Arrays;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public interface Preconditions {

    static final String REQUIRED = "%s is required";

    static final String REQUIRED_NOT_EMPTY = "%s cannot be empty";

    static final String NONE_NULL = "%s cannot contain null elements";

    static final String REQUIRED_NOT_BLANK = "%s cannot be blank";

    static final String OUT_OF_ORDER = "%s cannot be after %s";

    static final String FILE_MISSING = "%s is missing or cannot be read";

    static final String NOT_FILE = "%s must be a normal file";

    static final String NOT_DIRECTORY = "%s must be a directory";

    static final String NOT_PRESENT = "%s must be present";

    interface StateChecks {

        static void checkOrder(final Instant first, final Instant second, final String firstName, final String secondName) {
            checkState(!first.isAfter(second), OUT_OF_ORDER, firstName, secondName);
        }

        static void greaterThan(final int value, final int greaterThanValue, final String name) {
            checkState(value > greaterThanValue, "%s: (%s) must be greater than %s", name, value, greaterThanValue);
        }

        static void greaterThan(final long value, final long greaterThanValue, final String name) {
            checkState(value > greaterThanValue, "%s: (%s) must be greater than %s", name, value, greaterThanValue);
        }

        static void min(final int value, final int minValue, final String name) {
            checkState(value >= minValue, "%s: (%s) must be greater than or equal to %s", name, value, minValue);
        }

        static <A> void noneNull(final A[] obj, final String name) {
            checkState(ArrayUtils.isNotEmpty(obj), REQUIRED_NOT_EMPTY, name);
            checkState(Arrays.stream(obj).filter(o -> o == null).count() == 0, NONE_NULL, name);
        }

        static void notBlank(final String obj, final String name) {
            checkState(StringUtils.isNotBlank(obj), REQUIRED_NOT_BLANK, name);
        }

        static <A> void notEmpty(final A[] value, final String name) {
            checkState(ArrayUtils.isNotEmpty(value), REQUIRED_NOT_EMPTY, name);
        }

        static void notEmpty(final byte[] value, final String name) {
            checkState(ArrayUtils.isNotEmpty(value), REQUIRED_NOT_EMPTY, name);
        }

        static void required(final Object value, final String name) {
            checkState(value != null, REQUIRED, name);
        }

        static void exists(final File value, final String name) {
            checkState(value != null, REQUIRED, name);
            checkState(value.exists(), FILE_MISSING, name);
        }

        static void isFile(final File value, final String name) {
            exists(value, name);
            checkState(value.isFile(), NOT_FILE, name);
        }

        static void isDirectory(final File value, final String name) {
            exists(value, name);
            checkState(value.isDirectory(), NOT_DIRECTORY, name);
        }

        static <T> void isPresent(final Optional<T> optional, final String name) {
            checkState(optional.isPresent(), NOT_PRESENT, name);
        }
    }

    interface ArgumentChecks {

        static void checkOrder(final Instant first, final Instant second, final String firstName, final String secondName) {
            checkArgument(!first.isAfter(second), OUT_OF_ORDER, firstName, secondName);
        }

        static void greaterThan(final int value, final int greaterThanValue, final String name) {
            checkArgument(value > greaterThanValue, "%s: (%s) must be greater than %s", name, value, greaterThanValue);
        }

        static void greaterThan(final long value, final long greaterThanValue, final String name) {
            checkArgument(value > greaterThanValue, "%s: (%s) must be greater than %s", name, value, greaterThanValue);
        }

        static void min(final int value, final int minValue, final String name) {
            checkArgument(value >= minValue, "%s: (%s) must be greater than or equal to %s", name, value, minValue);
        }

        static void min(final long value, final long minValue, final String name) {
            checkArgument(value >= minValue, "%s: (%s) must be greater than or equal to %s", name, value, minValue);
        }

        static <A> void noneNull(final A[] obj, final String name) {
            checkArgument(ArrayUtils.isNotEmpty(obj), REQUIRED_NOT_EMPTY, name);
            checkArgument(Arrays.stream(obj).filter(o -> o == null).count() == 0, NONE_NULL, name);
        }

        static void notBlank(final String obj, final String name) {
            checkArgument(StringUtils.isNotBlank(obj), REQUIRED_NOT_BLANK, name);
        }

        static <A> void notEmpty(final A[] value, final String name) {
            checkArgument(ArrayUtils.isNotEmpty(value), REQUIRED_NOT_EMPTY, name);
        }

        static void notEmpty(final byte[] value, final String name) {
            checkArgument(ArrayUtils.isNotEmpty(value), REQUIRED_NOT_EMPTY, name);
        }
        static void required(final Object value, final String name) {
            checkArgument(value != null, REQUIRED, name);
        }

        static void exists(final File value, final String name) {
            checkArgument(value != null, REQUIRED, name);
            checkArgument(value.exists(), FILE_MISSING, name);
        }

        static void isFile(final File value, final String name) {
            exists(value, name);
            checkArgument(value.isFile(), NOT_FILE, name);
        }

        static void isDirectory(final File value, final String name) {
            exists(value, name);
            checkArgument(value.isDirectory(), NOT_DIRECTORY, name);
        }

        static <T> void isPresent(final Optional<T> optional, final String name) {
            checkArgument(optional.isPresent(), NOT_PRESENT, name);
        }
    }

}
