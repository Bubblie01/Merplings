package io.github.Bubblie.Merplings.Entities.BaoEntity;


import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import io.github.Bubblie.Merplings.Entities.ModSounds;
import io.github.Bubblie.Merplings.Structures.StructureData;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.GoatEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;


import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class AnnoyingEntity extends PathAwareEntity {

    private final EntityInventory inventory = new EntityInventory(8);

    protected Brain<AnnoyingEntity> brain;

    protected static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULES;
    private static final ImmutableList<SensorType<? extends Sensor<? super AnnoyingEntity>>> SENSORS;

    public AnnoyingEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        setCanPickUpLoot(true);


    }

    public void writeCustomDataToNbt(NbtCompound tag) {
        super.writeCustomDataToNbt(tag);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
    }

    protected static Brain<?> create(Brain<AnnoyingEntity> brain)
    {
        //addCoreActivities(brain);
        addIdleActivites(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.resetPossibleActivities();
        return brain;
    }

    protected Brain.Profile<AnnoyingEntity> createBrainProfile() {
        return Brain.createProfile(MEMORY_MODULES, SENSORS);
    }

    @Override
    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return AnnoyingEntity.create(this.createBrainProfile().deserialize(dynamic));
    }

    private static void addCoreActivities(Brain<AnnoyingEntity> brain) {
        brain.setTaskList(Activity.CORE, 0, ImmutableList.of(new StayAboveWaterTask(0.8F), new WalkTask(0.1F), new WanderAroundTask(), new LookAroundTask(45, 90)));
    }

    private static void addIdleActivites(Brain<AnnoyingEntity> brain)
    {
        brain.setTaskList(Activity.IDLE, ImmutableList.of(Pair.of(0, new TimeLimitedTask(new FollowMobTask(EntityType.PLAYER, 6.0F), UniformIntProvider.create(30, 60))),Pair.of(3, new RandomTask(ImmutableList.of(Pair.of(new StrollTask(0.1F), 2), Pair.of(new GoTowardsLookTarget(1.0F, 3), 2), Pair.of(new WaitTask(30, 60), 1))))));
    }

    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityTag) {
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
        //this.goalSelector.add(5, new WanderAroundFarGoal(this,0.35D,0.0F));
        //this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 12.0F));
        //this.goalSelector.add(6, new LookAroundGoal(this));
        this.goalSelector.add(1, new TemptGoal(this, 0.5D, Ingredient.fromTag(ItemTags.WOOL), false));
        this.goalSelector.add(2, new CraftBedGoal(this,0.5D));
        /*
        this.goalSelector.add(4, new MoveToTargetPosGoal(this,0.5D,8) {
            @Override
            protected boolean isTargetPos(WorldView world, BlockPos pos) {
                return world.getBlockState(pos).isOf(Blocks.CRAFTING_TABLE) && world.getBlockState(pos.up()).canPathfindThrough(world, pos, NavigationType.LAND);
            }
        });



    }
     */



    @Override
    protected void initDataTracker() {
        super.initDataTracker();
    }

    //Sound
    public SoundEvent getHurtSound(DamageSource source)
    {
        return ModSounds.BAO_SCREAM_EVENT;
    }

    public SoundEvent getAmbientSound()
    {
        return ModSounds.BAO_AMBIENT_EVENT;
    }

    public SoundEvent getDeathSound()
    {
        return ModSounds.BAO_DEATH_EVENT;
    }

    //Inventory
    public EntityInventory getInventory() {
        return this.inventory;
    }

    @Override
    protected void mobTick() {
        this.world.getProfiler().push("merpBrain");
        this.getBrain().tick((ServerWorld)this.world, this);
        super.mobTick();
    }

    @Override
    public Brain<AnnoyingEntity> getBrain() {
        return (Brain<AnnoyingEntity>) super.getBrain();
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
                    this.triggerItemPickedUpByEntityCriteria(item);
                    this.sendPickup(item, itemStack.getCount());
                    ItemStack itemStack2 = simpleInventory.addStack(itemStack);
                    if (itemStack2.isEmpty()) {
                        item.discard();
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

        System.out.println(getBrain().getRunningTasks());
        System.out.println(getBrain().getPossibleActivities());

        inventory.searchIngredient(ItemTags.WOOL);
        return ActionResult.SUCCESS;
    }
    @Override
    protected void sendAiDebugData() {
        super.sendAiDebugData();
        DebugInfoSender.sendBrainDebugData(this);
    }



    public static void registerAnnoyingEntity()
    {
        FabricDefaultAttributeRegistry.register(BAO,AnnoyingEntity.createMobAttributes().add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.5D));

    }

    static {
        SENSORS = ImmutableList.of(SensorType.NEAREST_PLAYERS, SensorType.NEAREST_ITEMS, SensorType.HURT_BY);
        MEMORY_MODULES = ImmutableList.of(MemoryModuleType.LOOK_TARGET, MemoryModuleType.VISIBLE_MOBS, MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.PATH, MemoryModuleType.ATE_RECENTLY, MemoryModuleType.TEMPTING_PLAYER, MemoryModuleType.TEMPTATION_COOLDOWN_TICKS, MemoryModuleType.IS_TEMPTED);
    }



}
