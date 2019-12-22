package com.jakchang.savelocation.Viewmodel;

import androidx.lifecycle.ViewModel;

public class MemoViewModel extends ViewModel {

    /*
    LiveData<List<MemoEntity>> memoModel;
    AppDatabase mRepository;

    public void init(Context context, String fromDate, String toDate){
        mRepository =  AppDatabase.getInstance(context);
        memoModel = mRepository.MemoDao().selectAll(fromDate,toDate);

    }

    public LiveData<List<MemoEntity>> memoList(){
        Log.d("TAG",memoModel.getValue().get(0).getId()+"");
        return memoModel;
    }
    public LiveData<List<MemoEntity>> memoListByTag(String tag, String fromDate, String toDate){
        memoModel = mRepository.MemoDao().selectAllByTag(tag,fromDate,toDate);
        return memoModel;
    }
*/
}