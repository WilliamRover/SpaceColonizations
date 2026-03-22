//package adapter;
//
//import android.view.View;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.spacecolonizations.R;
//
//import com.example.spacecolonizations.model.ship.EnemyShip;
//
//class EnemyShipViewHolder{
//static class ItemViewHolder extends RecyclerView.ViewHolder {
//    private final TextView txtViewHp;
//
//    public ItemViewHolder(@NonNull View itemView) {
//        super(itemView);
//        txtViewHp = itemView.findViewById(R.id.friendlyShipHP);
//    }
//
//    public void bind(EnemyShip ship) {
//        txtViewHp.setText(String.valueOf(ship.getHullStrength()));
//    }
//}
//}