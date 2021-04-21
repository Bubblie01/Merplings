package io.github.Bubblie.Merplings.Entities.BaoEntity;


import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import io.github.Bubblie.Merplings.Entities.EntitySounds;
import io.github.Bubblie.Merplings.Main;
import io.github.Bubblie.Merplings.mixin.SensorTypeAccessor;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HoglinBrain;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;


import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import io.github.Bubblie.Merplings.mixin.EntityBrainHelpers;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class AnnoyingEntity extends PathAwareEntity {
    private static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULES;
    private static final ImmutableList<SensorType<? extends Sensor<? super AnnoyingEntity>>> SENSORS;
    private int movementCooldownTicks;

    private final SimpleInventory inventory = new SimpleInventory(8);

    public AnnoyingEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        canMoveVoluntarily();
        canSee(this);
        setCanPickUpLoot(true);


    }
    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
    }

    public void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);
    }

    static
    {
        MEMORY_MODULES = ImmutableList.of(MemoryModuleType.HURT_BY_ENTITY, MemoryModuleType.WALK_TARGET, MemoryModuleType.INTERACTION_TARGET, MemoryModuleType.MOBS, MemoryModuleType.PATH, MemoryModuleType.LOOK_TARGET, MemoryModuleType.MEETING_POINT, MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER);
        SENSORS = ImmutableList.of(SensorType.NEAREST_ITEMS, SensorType.NEAREST_PLAYERS);
    }


    @Override
    public Brain<AnnoyingEntity> getBrain() {
        return (Brain<AnnoyingEntity>) super.getBrain();
    }

    @Override
    protected Brain.Profile<AnnoyingEntity> createBrainProfile() {
        return Brain.createProfile(MEMORY_MODULES, SENSORS);
    }

    @Override
    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return MerplingBrain.create(this.createBrainProfile().deserialize(dynamic));
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
    }

    @Override
    protected void mobTick()
    {
        this.world.getProfiler().push("merplingBrain");
        this.getBrain().tick((ServerWorld)this.world, this);
        this.world.getProfiler().pop();
        super.mobTick();
    }

    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable CompoundTag entityTag) {
        return super.initialize(world, difficulty, spawnReason, entityData, entityTag);
    }

    public static final EntityType<AnnoyingEntity> BAO = Registry.register(
            Registry.ENTITY_TYPE,
            //Do not capitalize namespace or path
            new Identifier("merp","bao"),
            //Hitbox and spawngroup
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, AnnoyingEntity::new).dimensions(EntityDimensions.fixed(0.6F,1.8F)).build()

    );



    /*
    @Override
    protected void initGoals()
    {
        this.goalSelector.add(7, new WanderAroundFarGoal(this,0.25D,0.0F));
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 12.0F));
        this.goalSelector.add(5, new LookAroundGoal(this));
        this.goalSelector.add(4,new MeleeAttackGoal(this,1.0D, false));
        this.targetSelector.add(5, new FollowTargetGoal<>(this, PlayerEntity.class,true));


    }

     */






    //Sound
    public SoundEvent getHurtSound(DamageSource source)
    {
        return EntitySounds.BAO_SCREAM_EVENT;
    }

    public SoundEvent getAmbientSound()
    {
        return EntitySounds.BAO_AMBIENT_EVENT;
    }

    public SoundEvent getDeathSound()
    {
        return EntitySounds.BAO_DEATH_EVENT;
    }

    //Inventory
    public SimpleInventory getInventory() {
        return this.inventory;
    }

    @Override
    protected void loot(ItemEntity item) {
        ItemStack itemStack = item.getStack();
        boolean decider = false;
            if((itemStack.getItem() instanceof SwordItem || itemStack.getItem() instanceof ToolItem) && tryEquip(itemStack))
            {
                decider = true;
            }
            if(itemStack.getItem() instanceof ArmorItem && tryEquip(itemStack))
            {
                decider = true;
            }


                if (this.canGather(itemStack)) {
                    SimpleInventory simpleInventory = this.getInventory();
                    boolean checkInsert = simpleInventory.canInsert(itemStack);
                    if (!checkInsert) {
                        return;
                    }
                    //
                    this.method_29499(item);
                    this.sendPickup(item, itemStack.getCount());
                    ItemStack itemStack2 = simpleInventory.addStack(itemStack);
                    if (itemStack2.isEmpty()) {
                        item.remove();
                    } else {
                        itemStack.setCount(itemStack2.getCount());
                    }
                    if(decider)
                    {
                        inventory.removeItem(item.getStack().getItem(), item.getStack().getCount());
                    }
                }


        }


    /*
    @Override
    public boolean tryAttack(Entity target) {
        return super.tryAttack(target);
    }

     */

    @Override
    public boolean canEquip(ItemStack stack) {
        return super.canEquip(stack);
    }

    @Override
    protected void dropInventory() {
        /*
        SimpleInventory simpleInventory = this.getInventory();
        for(int i = 0; i<simpleInventory.size(); i++)
        {
            dropStack(simpleInventory.getStack(i));
        }
         */
        this.inventory.clearToList().forEach(this::dropStack);
    }


    @Override
    public void onDeath(DamageSource source) {
        super.onDeath(source);
    }


    @Override
    public ActionResult interactMob(PlayerEntity user, Hand hand)
    {
        for(int i = 0; i<inventory.size(); i++)
        {
            System.out.println("Slot: " + i + ": " + inventory.getStack(i));
        }
        System.out.println("Equipment Slot " + getPreferredEquipmentSlot(inventory.getStack(0)));
        System.out.println("Equipped Stack " + getItemsEquipped());
        System.out.println(getBrain().hasMemoryModule(MemoryModuleType.INTERACTION_TARGET));
        System.out.println(getBrain().hasActivity(Activity.CORE));
        System.out.println(getBrain().getRunningTasks());
        System.out.println(getBrain().getFirstPossibleNonCoreActivity());
        System.out.println(getBrain());


        return ActionResult.SUCCESS;
    }
    @Override
    protected void sendAiDebugData() {
        super.sendAiDebugData();
        DebugInfoSender.sendBrainDebugData(this);
    }


    public static void registerAnnoyingEntity()
    {
        FabricDefaultAttributeRegistry.register(BAO,AnnoyingEntity.createMobAttributes().add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.4));

    }



}
