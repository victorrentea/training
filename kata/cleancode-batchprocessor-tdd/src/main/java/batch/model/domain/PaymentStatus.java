package batch.model.domain;

import java.util.EnumSet;

public enum PaymentStatus  {

	PREPAID, CREDITCARD_OPEN, CREDITCARD_PAID, TO_BE_INVOICED, INVOICED_OPEN, INVOICED_PAID, NOT_APPLICABLE;
	
	public static EnumSet<PaymentStatus> getFollowUpStatusses(PaymentStatus previous) {
		// Default
		EnumSet<PaymentStatus> followUpStatusses = EnumSet.of(NOT_APPLICABLE);
		if (previous != null) {
			followUpStatusses.add(previous);
		}

		if (CREDITCARD_OPEN == previous) {
			followUpStatusses.add(CREDITCARD_PAID);
		} else if (TO_BE_INVOICED == previous) {
			followUpStatusses.add(INVOICED_OPEN);
		} else if (INVOICED_OPEN == previous) {
			followUpStatusses.add(INVOICED_PAID);
		}

		return followUpStatusses;
	}

	public boolean isPriceNotMandatory() {
		return EnumSet.of(PREPAID, NOT_APPLICABLE).contains(this);
	}
}
