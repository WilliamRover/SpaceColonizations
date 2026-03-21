//package adapter;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.spacecolonizations.R;
//
//import java.util.List;
//
//import model.EnemyShip;
//import model.FriendlyShip;
//
//public class ShipAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//
//    private static final int TYPE_FRIENDLY = 0;
//    private static final int TYPE_ENEMY = 1;
//
//    private List<Object> shipList;
//
//    public ShipAdapter(List<Object> shipList) {
//        this.shipList = shipList;
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        if (shipList.get(position) instanceof FriendlyShip) {
//            return TYPE_FRIENDLY;
//        } else {
//            return TYPE_ENEMY;
//        }
//    }
//
//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        if (viewType == TYPE_FRIENDLY) {
//            View view = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.friendly_ship_stat, parent, false);
//            return new FriendlyShipViewHolder.ItemShipViewHolder(view);
//        } else {
//            View view = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.enemy_ship_stat, parent, false);
//            return new EnemyShipViewHolder.ItemViewHolder(view);
//        }
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        Object item = shipList.get(position);
//        if (holder instanceof FriendlyShipViewHolder.ItemShipViewHolder) {
//            ((FriendlyShipViewHolder.ItemShipViewHolder) holder).bind((FriendlyShip) item);
//        } else if (holder instanceof EnemyShipViewHolder.ItemViewHolder) {
//            ((EnemyShipViewHolder.ItemViewHolder) holder).bind((EnemyShip) item);
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return shipList.size();
//    }
//}
