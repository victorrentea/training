package batch.model.domain;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * A range of values. A range is a closed interval [min, max[.
 * <p>
 * A value range is a immutable value object.
 * 
 * @param <V> the value type; must be comparable
 */
@SuppressWarnings("serial")
public abstract class ValueRange<V extends Comparable<V>> implements Comparable<ValueRange<V>> {

	/**
	 * Returns the lower bound of the range.
	 * 
	 * @return the lower bound, or null if there is no lower bound
	 */
	public abstract V getMin();

	/**
	 * Returns the upper bound of the range.
	 * 
	 * @return the upper bound, or null if there is no upper bound
	 */
	public abstract V getMax();

	/**
	 * Returns whether or not this range is a closed interval.
	 * 
	 * @return true or false
	 */
	public boolean isClosed() {
		return getMin() != null && getMax() != null;
	}

	/**
	 * Check if given value is in this range. Note that this check considers the bounds as being 'in' the range. In other words, the interval is
	 * <i>closed</i>. So the executed check is 'v in [min, max]'.
	 * 
	 * @param v the value to check
	 * @return whether or not given value is in this range
	 */
	public boolean contains(V v) {
		return (getMin() == null || getMin().compareTo(v) <= 0) && (getMax() == null || getMax().compareTo(v) > 0);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		} else if (obj instanceof ValueRange) {
			ValueRange<?> other = (ValueRange<?>) obj;
			return new EqualsBuilder().append(this.getMin(), other.getMin()).append(this.getMax(), other.getMax()).isEquals();
		} else {
			return false;
		}
	}

	@Override
	public int compareTo(ValueRange<V> obj) {
		if (obj == this) {
			return 0;
		} else {
			return new CompareToBuilder().append(this.getMin(), obj.getMin()).append(this.getMax(), obj.getMax()).toComparison();
		}
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getMin()).append(getMax()).toHashCode();
	}

	@Override
	public String toString() {
		return "[" + getMin() + ", " + getMax() + "[";
	}
	
	public abstract String getFormattedRange();
}
