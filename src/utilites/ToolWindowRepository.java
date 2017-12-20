package utilites;

import views.ClonedCodeView;
import views.ClonesView;

public class ToolWindowRepository {

    private static ToolWindowRepository repository = null;
    private ClonesView view ;
    private ClonedCodeView codeView;

    private ToolWindowRepository() {

    }

    public static ToolWindowRepository getInstance(){
        if(repository == null){
            repository = new ToolWindowRepository();
        }
        return repository;
    }

    public void setView(ClonesView view) {
        this.view = view;
    }

    public ClonesView getView() {
        return view;
    }

    public ClonedCodeView getCodeView() {
        return codeView;
    }

    public void setCodeView(ClonedCodeView codeView) {
        this.codeView = codeView;
    }
}
