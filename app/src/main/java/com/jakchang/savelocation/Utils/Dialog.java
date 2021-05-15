package com.jakchang.savelocation.Utils;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.jakchang.savelocation.Entity.MemoEntity;
import com.jakchang.savelocation.Interface.Callback;
import com.jakchang.savelocation.Repository.AppDatabase;

public class Dialog {
    Context mContext;
    int seletedId;
    static Dialog dialog;
    public Dialog(){}

    public static Dialog getInstance(Context context){
        if(dialog==null) dialog=new Dialog(context);
        return dialog;
    }

    public Dialog(Context context){
        this.mContext=context;
    }

    public void exitDialog(final Callback callback){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage("종료하시겠습니까?");
        AlertDialog alertDialog;
        builder.setPositiveButton("예", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id){
                callback.success();
            }
        });

        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }

    public void deleteDialog(int id, final Callback callback){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        seletedId =id;
        builder.setMessage("삭제하시겠습니까?");
        AlertDialog alertDialog;
        builder.setPositiveButton("예", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id){
                Toast.makeText(mContext, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                MemoEntity memo = AppDatabase.getInstance(mContext).MemoDao().selectOne(seletedId);
                memo.setIsDeleted("true");
                AppDatabase.getInstance(mContext).MemoDao().updateMemo(memo);
                callback.success();
            }
        });

        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(mContext, "취소되었습니다.", Toast.LENGTH_SHORT).show();

            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }

    public void onCanceled(){
        dialog=null;
    }

    public void updateDialog(int id,final Callback callback){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        seletedId =id;
        builder.setMessage("복구하시겠습니까?");
        AlertDialog alertDialog;
        builder.setPositiveButton("예", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id){
                Toast.makeText(mContext, "복구되었습니다.", Toast.LENGTH_SHORT).show();
                MemoEntity memo = AppDatabase.getInstance(mContext).MemoDao().selectOne(seletedId);
                memo.setIsDeleted("false");
                AppDatabase.getInstance(mContext).MemoDao().updateMemo(memo);
                callback.success();
            }
        });

        builder.setNeutralButton("삭제", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id){
                Toast.makeText(mContext, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                MemoEntity memo = AppDatabase.getInstance(mContext).MemoDao().selectOne(seletedId);
                AppDatabase.getInstance(mContext).MemoDao().deleteMemo(memo);
                callback.success();
            }
        });

        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //Toast.makeText(mContext, "취소되었습니다.", Toast.LENGTH_SHORT).show();

            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }
}
