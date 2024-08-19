package com.cv.designvision.models.patch;

/**
 * This class extends {@link ViewPatch}, provides an index-able
 * Patch object with visual highlighting.
 */
public class SelectableViewPatch extends ViewPatch {

    private static final String FX_HIGHLIGHT = "lime";
    private final int patchIdx;
    private final int padding;

    /**
     * Constructs a new SelectableViewPatch instance based on a provided {@link IPatch}
     * object, sets the size of the visual representation, and assigns an index
     * to identify this patch within a collection.
     *
     * @param patch The Patch object to use as source.
     * @param size The desired size (width and height) of the SelectableViewPatch.
     * @param patchIdx An index to uniquely identify this patch.
     */
    public SelectableViewPatch(IPatch patch, int size, int patchIdx) {
        super(patch, size);
        this.patchIdx = patchIdx;
        padding = size/3;
        setStyle("-fx-padding: "+ padding + "px");
    }

    /**
     * Enables or disables the visual highlight for this SelectableViewPatch.
     *
     * @param enable True to enable the highlight, false to disable it.
     */
    public void setHighlight(boolean enable) {
        if (enable) {
            //setStyle("-fx-background-color: aqua; -fx-padding: 10px;");
            setStyle("-fx-background-color: radial-gradient(center 50% 50%, radius 50% , " +
                    FX_HIGHLIGHT + ", transparent); " +
                    "-fx-padding: " + padding + "px");
        } else {
            setStyle("-fx-background-color: transparent; " +
                    "-fx-padding: " + padding + "px");
        }
    }

    /**
     * Returns the index assigned during construction.
     *
     * @return The integer index associated with this SelectableViewPatch.
     */
    public int getPatchIdx(){
        return patchIdx;
    }
}
