/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.Objects;

/**
 * Container to ease passing around a tuple of two objects. This object provides a sensible
 * implementation of equals(), returning true if equals() is true on each of the contained
 * objects.
 *
 * @link https://android.googlesource.com/platform/frameworks/base/+/master/core/java/android/util/Pair.java
 */
public class Couple<F, S> {
    public final F first;
    public final S second;

    /**
     * Constructor for a Couple.
     *
     * @param first  the first object in the Couple
     * @param second the second object in the Couple
     */
    public Couple(F first, S second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Checks the two objects for equality by delegating to their respective
     * {@link Object#equals(Object)} methods.
     *
     * @param o the {@link Couple} to which this one is to be checked for equality
     * @return true if the underlying objects of the Couple are both considered
     * equal
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Couple)) {
            return false;
        }
        Couple<?, ?> p = (Couple<?, ?>) o;
        return Objects.equals(p.first, first) && Objects.equals(p.second, second);
    }

    /**
     * Compute a hash code using the hash codes of the underlying objects
     *
     * @return a hashcode of the Couple
     */
    @Override
    public int hashCode() {
        return (first == null ? 0 : first.hashCode()) ^ (second == null ? 0 : second.hashCode());
    }

    @Override
    public String toString() {
        return "Couple{" + String.valueOf(first) + " " + String.valueOf(second) + "}";
    }

    /**
     * Convenience method for creating an appropriately typed couple.
     *
     * @param a the first object in the Couple
     * @param b the second object in the Couple
     * @return a Couple that is templatized with the types of a and b
     */
    public static <A, B> Couple<A, B> create(A a, B b) {
        return new Couple<A, B>(a, b);
    }
}
