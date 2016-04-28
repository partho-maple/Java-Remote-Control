package MARC.myjavaproject.Viewer.FileMng;

import MARC.myjavaproject.ViewerHelper.Recorder;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.util.List;
import javax.swing.JLabel;

//import RDP_Util.viewer.Recorder;


public class FilesDropTargetListener implements DropTargetListener {

    private Recorder recorder;
    
    public FilesDropTargetListener(JLabel label, Recorder recorder) {
        this.label = label;
        this.recorder = recorder;
    }

    public void dragEnter(DropTargetDragEvent event) {
        if (!isDragAcceptable(event)) {
            event.rejectDrag();
            return;
        }
    }

    public void dragExit(DropTargetEvent event) {}

    public void dragOver(DropTargetDragEvent event) {}

    public void dropActionChanged(DropTargetDragEvent event) {
        if (!isDragAcceptable(event)) {
            event.rejectDrag();
            return;
        }
    }

    public void drop(DropTargetDropEvent event) {
        if (!isDropAcceptable(event)) {
            event.rejectDrop();
            return;
        }

        event.acceptDrop(DnDConstants.ACTION_COPY);

        Transferable content = event.getTransferable();
        if (content == null) return;

        if (recorder.isRecording())
            try {
                if (content.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                    List list = (List) content.getTransferData(DataFlavor.javaFileListFlavor);
                    File[] files = (File[]) list.toArray().clone();
                    if (files == null) return;
                    recorder.fileManager.setFiles(files);
                    recorder.viewer.AddObject(new File("."));
                }
            } catch (Exception e) {
                e.getStackTrace();
            }

        event.dropComplete(true);
    }

    public boolean isDragAcceptable(DropTargetDragEvent event) {
        return (event.getDropAction() & DnDConstants.ACTION_COPY_OR_MOVE) != 0;
    }

    public boolean isDropAcceptable(DropTargetDropEvent event) {
        return (event.getDropAction() & DnDConstants.ACTION_COPY_OR_MOVE) != 0;
    }
    private JLabel label;
}
