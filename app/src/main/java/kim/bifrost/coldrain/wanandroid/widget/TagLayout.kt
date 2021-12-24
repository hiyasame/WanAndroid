package kim.bifrost.coldrain.wanandroid.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import kim.bifrost.coldrain.wanandroid.utils.getMarginLayoutParams

/**
 * kim.bifrost.coldrain.wanandroid.widget.TagLayout
 * WanAndroid
 *
 * @author 寒雨
 * @since 2021/12/21 13:10
 **/
class TagLayout(context: Context, attrs: AttributeSet) : ViewGroup(context, attrs) {

    private val lineHeights = arrayListOf<Int>()
    private val views = arrayListOf<ArrayList<View>>()

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        views.clear()
        lineHeights.clear()
        var lineViews = arrayListOf<View>()
        val width = measuredWidth
        var lineWidth = 0
        var lineHeight = 0
        val childCount = childCount
        for (i in (0 until childCount)) {
            val child = getChildAt(i)
            val lp = child.layoutParams as MarginLayoutParams
            // 超出则换行
            if (child.measuredWidth + lp.leftMargin + lp.rightMargin + lineWidth > width) {
                lineHeights.add(lineHeight)
                views.add(lineViews)
                lineHeight = 0
                lineWidth = 0
                lineViews = arrayListOf()
            }
            lineWidth += child.measuredWidth + lp.leftMargin + lp.rightMargin
            lineHeight = lineHeight.coerceAtLeast(child.measuredHeight + lp.topMargin + lp.bottomMargin)
            lineViews.add(child)
        }
        lineHeights.add(lineHeight)
        views.add(lineViews)
        var left = 0
        var top = 0
        for (i in (0 until views.size)) {
            lineViews = views[i]
            lineHeight = lineHeights[i]
            for (j in 0 until lineViews.size) {
                val child = lineViews[j]
                val lp = child.layoutParams as MarginLayoutParams
                child.layout(
                    left + lp.leftMargin,
                    top + lp.topMargin,
                    left + lp.leftMargin + child.measuredWidth,
                    top + lp.topMargin + child.measuredHeight
                )
                left += child.measuredWidth + lp.leftMargin + lp.rightMargin
            }
            left = 0
            top += lineHeight
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val sizeWidth = MeasureSpec.getSize(widthMeasureSpec)
        val sizeHeight = MeasureSpec.getSize(heightMeasureSpec)
        val modeWidth = MeasureSpec.getMode(widthMeasureSpec)
        val modeHeight = MeasureSpec.getMode(heightMeasureSpec)

        var width = 0 //width=所有行里面最宽的一行

        var height = 0 //height=所有行的高度相加

        //一行的宽度=一行当中的所有view的宽度的和
        var lineWidth = 0
        var lineHeight = 0

        //1.测量所有子控件的大小
        val childCount = childCount
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val lp = child.getMarginLayoutParams()
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0)
            //子控件真实占用的宽和高度
            val childWidth = child.measuredWidth + lp.leftMargin + lp.rightMargin
            val childHeight = child.measuredHeight + lp.topMargin + lp.bottomMargin
            //当一行放不下的时候需要换行
            if (lineWidth + childWidth > sizeWidth) {
                //换行
                width = Math.max(lineWidth, width)
                lineWidth = childWidth
                height += lineHeight
                lineHeight = childHeight
            } else { //累加
                lineWidth += childWidth
                lineHeight = Math.max(lineHeight, childHeight)
            }
            //最后一步
            if (i == childCount - 1) {
                width = Math.max(width, lineWidth)
                height += lineHeight
            }
        }

        //2.测量并定义自身的大小
        val measuredWidth = if (modeWidth == MeasureSpec.EXACTLY) sizeWidth else width
        val measuredHeight = if (modeHeight == MeasureSpec.EXACTLY) sizeHeight else height
        setMeasuredDimension(measuredWidth, measuredHeight)
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    override fun generateLayoutParams(p: LayoutParams?): LayoutParams {
        return MarginLayoutParams(p)
    }
}