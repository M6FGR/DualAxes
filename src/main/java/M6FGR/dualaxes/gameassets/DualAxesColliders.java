//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package M6FGR.dualaxes.gameassets;

import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.collider.MultiOBBCollider;

public class DualAxesColliders {
    public static final Collider SLASH = new MultiOBBCollider(3, 1.5, 1.0, 1.5, 0.0, 1.5, -1.0);
    public static final Collider AXE_SLAM = new MultiOBBCollider(2, 0.7, 0.7, 0.7, 0.0, 1.0, -1.0);
    public static final Collider SLAM = new MultiOBBCollider(6, 2.0, 1.5, 2.0, 0.0, 1.0, -1.0);

    public DualAxesColliders() {
    }
}