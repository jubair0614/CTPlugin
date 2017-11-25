package utilites;

import views.ClonesView;

public class ToolWindowRepository {

    private static ToolWindowRepository repository = null;
    private ClonesView view ;

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
}
