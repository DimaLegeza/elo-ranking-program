package org.homemade.elo.entities.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.homemade.elo.enums.Order;

import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true, value = {"propertyName"})
@Getter
@NoArgsConstructor
public class PlayerWithProperty extends BasePlayer {
	protected static final String FLOAT_MESSAGE = "%s: %s - %.3f (rank - %d)";
	protected static final String INT_MESSAGE = "%s: %s - %.0f (rank - %d)";
	private Double propertyValue;
	private String propertyName;

	public PlayerWithProperty(String name, int rank, double property, Order order) {
		super(name, rank);
		this.propertyValue = property;
		this.propertyName = order != null ? order.getName() : "Unknown property";
	}

	@Override
	public String formatString(int nameLength) {
		if ((this.propertyValue == null && this.propertyName == null) || Order.RANK.getName().equals(this.propertyName)) {
			return super.formatString(nameLength);
		}
		return String.format(
			this.isValueInt(this.propertyValue) ? PlayerWithProperty.INT_MESSAGE : PlayerWithProperty.FLOAT_MESSAGE,
			this.reformatName(nameLength),
			this.propertyName,
			this.propertyValue,
			this.getRank()
		);
	}

	private boolean isValueInt(double value) {
		return (value == Math.floor(value)) && !Double.isInfinite(value);
	}

}
