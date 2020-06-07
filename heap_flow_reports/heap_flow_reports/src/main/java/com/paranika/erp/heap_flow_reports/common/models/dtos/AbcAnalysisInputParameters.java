package com.paranika.erp.heap_flow_reports.common.models.dtos;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.paranika.erp.heap_flow_reports.common.AppConstants;

public class AbcAnalysisInputParameters implements Comparable<AbcAnalysisInputParameters> {

	private String categoryName;
	private Double minValue;
	private Double maxValue;

	public AbcAnalysisInputParameters() {

	}

	public AbcAnalysisInputParameters(String categoryName, Double minValue, Double maxValue) {
		this.categoryName = categoryName;
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Double getMinValue() {
		return minValue;
	}

	public void setMinValue(Double minValue) {
		this.minValue = minValue;
	}

	public Double getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Double maxValue) {
		this.maxValue = maxValue;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AbcAnalysisInputParameters [categoryName=");
		builder.append(categoryName);
		builder.append(", minValue=");
		builder.append(minValue);
		builder.append(", maxValue=");
		builder.append(maxValue);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categoryName == null) ? 0 : categoryName.hashCode());
		result = prime * result + ((maxValue == null) ? 0 : maxValue.hashCode());
		result = prime * result + ((minValue == null) ? 0 : minValue.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbcAnalysisInputParameters other = (AbcAnalysisInputParameters) obj;
		if (categoryName == null) {
			if (other.categoryName != null)
				return false;
		} else if (!categoryName.equals(other.categoryName))
			return false;
		if (maxValue == null) {
			if (other.maxValue != null)
				return false;
		} else if (!maxValue.equals(other.maxValue))
			return false;
		if (minValue == null) {
			if (other.minValue != null)
				return false;
		} else if (!minValue.equals(other.minValue))
			return false;
		return true;
	}

	@Override
	public int compareTo(AbcAnalysisInputParameters incoming) {
		if (incoming == null || incoming.getMinValue() == null) {
			return 1;
		} else {
			if (this.minValue == null) {
				return -1;
			}
			return this.minValue.compareTo(incoming.getMinValue());
		}
	}

	public static void main(String[] args) {
		List<AbcAnalysisInputParameters> boundaries = new ArrayList<AbcAnalysisInputParameters>();
		AbcAnalysisInputParameters typeA = new AbcAnalysisInputParameters("Type A", Double.MIN_VALUE,
				AppConstants.DEFAULT_MAX_A_VALUE);
		AbcAnalysisInputParameters typeB = new AbcAnalysisInputParameters("Type B", AppConstants.DEFAULT_MAX_A_VALUE,
				AppConstants.DEFAULT_MAX_B_VALUE);
		AbcAnalysisInputParameters typeC = new AbcAnalysisInputParameters("Type C", AppConstants.DEFAULT_MIN_C_VALUE,
				Double.MAX_VALUE);
		boundaries.add(typeA);
		boundaries.add(typeB);
		boundaries.add(typeC);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonInString = gson.toJson(boundaries);

		System.out.println(jsonInString);
	}

}
