package org.molgenis.compute.ui.meta;

import org.molgenis.data.support.DefaultEntityMetaData;

public class AnalysisTargetMetaData extends DefaultEntityMetaData
{
	public static final AnalysisTargetMetaData INSTANCE = new AnalysisTargetMetaData();

	private static final String ENTITY_NAME = "AnalysisTarget";
	public static final String IDENTIFIER = "identifier";
	public static final String TARGET_ID = "targetId";

	private AnalysisTargetMetaData()
	{
		super(ENTITY_NAME, ComputeUiPackage.INSTANCE);

		addAttribute(IDENTIFIER).setIdAttribute(true).setNillable(false).setVisible(false);
		addAttribute(TARGET_ID).setLabelAttribute(true).setNillable(false);
	}
}
