package com.example.teamalpha10.Adpter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.teamalpha10.CommentActivity;
import com.example.teamalpha10.FollowersActivity;
import com.example.teamalpha10.Fragment.ProfileFragment;
import com.example.teamalpha10.R;
import com.example.teamalpha10.model.Post;
import com.example.teamalpha10.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>{

    public Context mContext;
    public List<Post> mPost;

    public boolean dj=false;

    private FirebaseUser firebaseUser;

    public PostAdapter(Context mContext, List<Post> mPost) {
        this.mContext = mContext;
        this.mPost = mPost;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.post_item,viewGroup,false);
        return new PostAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        final Post post=mPost.get(i);
        Glide.with(mContext).load(post.getPostimage()).into(viewHolder.post_image);


        viewHolder.description.setText(post.getDescription());
        viewHolder.time.setText("Time: "+post.getTime());
        viewHolder.dete.setText("Date: "+post.getDate());
        viewHolder.title.setText(post.getTile());



        publisherInfo(viewHolder.image_profile,viewHolder.username,viewHolder.publisher,post.getPublisher());


        isintrast(post.getPostid(),viewHolder.intrast,viewHolder.notgoing,viewHolder.like);
        nrintrast(viewHolder.intrasts,viewHolder.notgoings,viewHolder.likes,post.getPostid());
        getComments(post.getPostid(),viewHolder.comments);
        issaved(post.getPostid(),viewHolder.save);


        viewHolder.image_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor=mContext.getSharedPreferences("PREFS",Context.MODE_PRIVATE).edit();
                editor.putString("profileid",post.getPublisher());
                editor.apply();
                ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfileFragment()).commit();
            }
        });
        viewHolder.publisher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor=mContext.getSharedPreferences("PREFS",Context.MODE_PRIVATE).edit();
                editor.putString("profileid",post.getPublisher());
                editor.apply();
                ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfileFragment()).commit();
            }
        });

        viewHolder.intrast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewHolder.intrast.getTag().equals("intrast")){
                    FirebaseDatabase.getInstance().getReference().child("response").child("intrast").child(post.getPostid())
                            .child(firebaseUser.getUid()).setValue(true);
                    FirebaseDatabase.getInstance().getReference().child("response").child("notgoing").child(post.getPostid())
                            .child(firebaseUser.getUid()).removeValue();
                    FirebaseDatabase.getInstance().getReference().child("response").child("going").child(post.getPostid())
                            .child(firebaseUser.getUid()).removeValue();

                }else {
                    FirebaseDatabase.getInstance().getReference().child("response").child("intrast").child(post.getPostid())
                            .child(firebaseUser.getUid()).removeValue();

                }
            }
        });
        viewHolder.notgoing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewHolder.notgoing.getTag().equals("notgoing")){
                    FirebaseDatabase.getInstance().getReference().child("response").child("notgoing").child(post.getPostid())
                            .child(firebaseUser.getUid()).setValue(true);
                    FirebaseDatabase.getInstance().getReference().child("response").child("intrast").child(post.getPostid())
                            .child(firebaseUser.getUid()).removeValue();
                    FirebaseDatabase.getInstance().getReference().child("response").child("going").child(post.getPostid())
                            .child(firebaseUser.getUid()).removeValue();

                }else {
                    FirebaseDatabase.getInstance().getReference().child("response").child("notgoing").child(post.getPostid())
                            .child(firebaseUser.getUid()).removeValue();

                }
            }
        });
        viewHolder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.like.getTag().equals("going")){
                    FirebaseDatabase.getInstance().getReference().child("response").child("going").child(post.getPostid())
                            .child(firebaseUser.getUid()).setValue(true);
                    FirebaseDatabase.getInstance().getReference().child("response").child("intrast").child(post.getPostid())
                            .child(firebaseUser.getUid()).removeValue();
                    FirebaseDatabase.getInstance().getReference().child("response").child("notgoing").child(post.getPostid())
                            .child(firebaseUser.getUid()).removeValue();
                }else {
                    FirebaseDatabase.getInstance().getReference().child("response").child("going").child(post.getPostid())
                            .child(firebaseUser.getUid()).removeValue();

                }
            }
        });
        viewHolder.likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, FollowersActivity.class);
                intent.putExtra("id",post.getPostid());
                intent.putExtra("title","going");
                mContext.startActivity(intent);
            }
        });
        viewHolder.notgoings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, FollowersActivity.class);
                intent.putExtra("id",post.getPostid());
                intent.putExtra("title","not going");
                mContext.startActivity(intent);
            }
        });
        viewHolder.intrasts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, FollowersActivity.class);
                intent.putExtra("id",post.getPostid());
                intent.putExtra("title","intrasts");
                mContext.startActivity(intent);
            }
        });
        viewHolder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, CommentActivity.class);
                intent.putExtra("postid",post.getPostid());
                intent.putExtra("publisher",post.getPublisher());
                mContext.startActivity(intent);
            }
        });
        viewHolder.comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, CommentActivity.class);
                intent.putExtra("postid",post.getPostid());
                intent.putExtra("publisher",post.getPublisher());
                mContext.startActivity(intent);
            }

        });
        viewHolder.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewHolder.save.getTag().equals("save")){
                    FirebaseDatabase.getInstance().getReference().child("Saves").child(firebaseUser.getUid())
                            .child(post.getPostid()).setValue(true);
                }else {
                    FirebaseDatabase.getInstance().getReference().child("Saves").child(firebaseUser.getUid())
                            .child(post.getPostid()).removeValue();
                }
            }
        });
        viewHolder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu=new PopupMenu(mContext,v);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.edit:
                            editPost(post.getPostid());
                            return true;
                            case R.id.delete :
                                FirebaseDatabase.getInstance().getReference("Posts")
                                        .child(post.getPostid()).removeValue()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(mContext,"Deleted!",Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                return true;
                                default:
                                    return false;
                        }
                    }
                });
                popupMenu.inflate(R.menu.post_menu);
                if(!post.getPublisher().equals(firebaseUser.getUid())){
                    popupMenu.getMenu().findItem(R.id.edit).setVisible(false);
                    popupMenu.getMenu().findItem(R.id.delete).setVisible(false);

                }
                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPost.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView image_profile,post_image,like,comment,save,intrast,notgoing,more;
        public TextView username,likes,publisher,description,comments,intrasts,notgoings;
        public TextView time,location,dete,title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image_profile=itemView.findViewById(R.id.image_profile);
            post_image=itemView.findViewById(R.id.post_image);
            comments=itemView.findViewById(R.id.comments);
            like=itemView.findViewById(R.id.like);
            comment=itemView.findViewById(R.id.comment);
            save=itemView.findViewById(R.id.save);
            username=itemView.findViewById(R.id.username);
            likes=itemView.findViewById(R.id.likes);
            publisher=itemView.findViewById(R.id.publisher);
            description=itemView.findViewById(R.id.description);
            intrast=itemView.findViewById(R.id.intrast);
            intrasts=itemView.findViewById(R.id.intrasts);
            notgoing=itemView.findViewById(R.id.notgoing);
            notgoings=itemView.findViewById(R.id.notgoings);
            time=itemView.findViewById(R.id.time);
            location=itemView.findViewById(R.id.location);
            dete=itemView.findViewById(R.id.date);
            title=itemView.findViewById(R.id.title);
            more=itemView.findViewById(R.id.more);



        }
    }
    private void getComments(String postid, final TextView comments){
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("Comments").child(postid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                comments.setText("view All"+dataSnapshot.getChildrenCount()+" Comments");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void isintrast(String postid, final ImageView imageView1, final ImageView imageView2,final ImageView imageView3){
        final FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference referencegoing=FirebaseDatabase.getInstance().getReference()
                .child("response")
                .child("going")
                .child(postid);
        DatabaseReference referenceintrast=FirebaseDatabase.getInstance().getReference()
                .child("response")
                .child("intrast")
                .child(postid);
        DatabaseReference referencenotgoing=FirebaseDatabase.getInstance().getReference()
                .child("response")
                .child("notgoing")
                .child(postid);
        referencegoing.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(firebaseUser.getUid()).exists()){
                    imageView3.setImageResource(R.drawable.ic_oked);
                    imageView3.setTag("goingON");
                }else {
                    imageView3.setImageResource(R.drawable.ic_ok);
                    imageView3.setTag("going");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        referencenotgoing.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(firebaseUser.getUid()).exists()){
                    imageView2.setImageResource(R.drawable.ic_no_on);
                    imageView2.setTag("notgoingON");
                }else {
                    imageView2.setImageResource(R.drawable.ic_no);
                    imageView2.setTag("notgoing");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        referenceintrast.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
              if (dataSnapshot.child(firebaseUser.getUid()).exists()){
                  imageView1.setImageResource(R.drawable.ic_maybeno);
                  imageView1.setTag("intrasted");
              }else {
                  imageView1.setImageResource(R.drawable.ic_maybe);
                  imageView1.setTag("intrast");
              }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
    private void nrintrast(final TextView intrast,final TextView notgoing,final TextView going, String postid){
        DatabaseReference referencenotgoing=FirebaseDatabase.getInstance().getReference().child("response").child("notgoing")
                .child(postid);
        referencenotgoing.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                notgoing.setText(dataSnapshot.getChildrenCount()+" Not going");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        DatabaseReference referencegoing=FirebaseDatabase.getInstance().getReference().child("response").child("going")
                .child(postid);
        referencegoing.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                going.setText(dataSnapshot.getChildrenCount()+" going");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        DatabaseReference referenceintrast=FirebaseDatabase.getInstance().getReference().child("response").child("intrast")
                .child(postid);
        referenceintrast.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               intrast.setText(dataSnapshot.getChildrenCount()+" intrasted");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void publisherInfo(final ImageView image_profile, final TextView username, final TextView publisher, final String userid){
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("users").child(userid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user=dataSnapshot.getValue(User.class);
                Glide.with(mContext).load(user.getImageurl()).into(image_profile);
                username.setText(user.getUsername());
                publisher.setText(user.getFullname());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void issaved(final String postid, final ImageView imageView){
        FirebaseUser firebaseUse=FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Saves")
                .child(firebaseUse.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(postid).exists()){
                    imageView.setImageResource(R.drawable.ic_saved);
                    imageView.setTag("saved");
                }else {
                    imageView.setImageResource(R.drawable.ic_save);
                    imageView.setTag("save");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void editPost(final String postid){
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(mContext);
        alertDialog.setTitle("Edit Post");

        final EditText editText=new EditText(mContext);
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        editText.setLayoutParams(lp);
        alertDialog.setView(editText);

        getText(postid,editText);
        alertDialog.setPositiveButton("Edit",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HashMap<String,Object>hashMap=new HashMap<>();
                        hashMap.put("description",editText.getText().toString());
                        FirebaseDatabase.getInstance().getReference("Posts")
                                .child(postid).updateChildren(hashMap);
                    }
                });
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
    private void getText(String postid,final EditText editText){
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Posts")
                .child(postid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                editText.setText(dataSnapshot.getValue(Post.class).getDescription());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
