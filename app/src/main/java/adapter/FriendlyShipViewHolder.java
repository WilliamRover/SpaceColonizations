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
//import com.example.spacecolonizations.model.ship.FriendlyShip;
//
//class FriendlyShipViewHolder {
//    static class ItemShipViewHolder extends RecyclerView.ViewHolder {
//        private final TextView txtViewHp;
//        private final TextView txtViewEnergy;
//        public ItemShipViewHolder(@NonNull View itemView) {
//            super(itemView);
//            txtViewHp = itemView.findViewById(R.id.friendlyShipHP);
//            txtViewEnergy = itemView.findViewById(R.id.friendlyShipEnergy);
//
//        }
//        public void bind(FriendlyShip ship) {
//            txtViewHp.setText(String.valueOf(ship.getHullStrength()));
//            txtViewEnergy.setText(String.valueOf(ship.getEnergy()));
//        }
//    }
//}