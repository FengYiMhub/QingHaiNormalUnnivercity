package cn.edu.qhnu.qhsfdx.widget;

import cn.edu.qhnu.qhsfdx.widget.ViewFlow.ViewSwitchListener;
public interface FlowIndicator extends ViewSwitchListener {
	public void setViewFlow(ViewFlow view);
	public void onScrolled(int h, int v, int oldh, int oldv);
}
