package com.paranika.erp.heap_flow_reports.common.models.dtos;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.paranika.erp.heap_flow_reports.common.AppConstants;

public class AbcAnalysisInput {
	List<AbcAnalysisInputParameters> inputList;

	public List<AbcAnalysisInputParameters> getInputList() {
		return inputList;
	}

	public void setInputList(List<AbcAnalysisInputParameters> inputList) {
		this.inputList = inputList;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AbcAnalysisInput [inputList=");
		builder.append(inputList);
		builder.append("]");
		return builder.toString();
	}

	public static void main(String[] args) {
		AbcAnalysisInput input = new AbcAnalysisInput();

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

		input.setInputList(boundaries);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonInString = gson.toJson(input);

		System.out.println(jsonInString);
	}

}
