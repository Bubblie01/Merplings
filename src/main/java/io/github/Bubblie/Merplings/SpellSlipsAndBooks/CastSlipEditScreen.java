package io.github.Bubblie.Merplings.SpellSlipsAndBooks;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import io.github.Bubblie.Merplings.Entities.ModSounds;
import io.github.Bubblie.Merplings.Main;
import io.netty.util.internal.StringUtil;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.SharedConstants;
import net.minecraft.client.font.MultilineText;
import net.minecraft.client.font.TextHandler;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.BookEditScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.client.util.SelectionManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Rect2i;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.BookUpdateC2SPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.*;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.mutable.MutableInt;
import org.lwjgl.glfw.GLFW;

import java.util.*;

public class CastSlipEditScreen extends Screen {
    public static final Identifier SLIP_TEXTURE = new Identifier("merp","textures/gui/slip.png");
    private final PlayerEntity player;
    private final ItemStack itemStack;
    private final Hand hand;
    private int tickCounter;
    private final Identifier LINES_PATH = new Identifier(Main.MOD_ID, "lines");
    private final int wordsPerLine = 25;
    private String slipWords = "";
    private int wordCursor = 0;
    private List<String> Lines = new ArrayList<String>();
    private final SelectionManager slipSelectionManager = new SelectionManager(this::getSlipWords, this::setSlipWords,this::getClipboard,this::setClipboard,(string) -> {
        return string.length() < 1024 && this.textRenderer.getWrappedLinesHeight(string, 114) <= 50;});
    private ButtonWidget castButton;
    private ButtonWidget cancelButton;
    public static final Text CAST = new TranslatableText("Cast");

    protected CastSlipEditScreen(PlayerEntity player, ItemStack itemStack, Hand hand) {
        super(NarratorManager.EMPTY);
        this.player = player;
        this.itemStack = itemStack;
        this.hand = hand;
    }

    private String getSlipWords()
    {
        return this.slipWords;
    }

    private void setSlipWords(String content)
    {
        this.slipWords = content;
    }

    private void setClipboard(String clipboard) {
        if (this.client != null) {
            SelectionManager.setClipboard(this.client, clipboard);
        }

    }

    private String getClipboard() {
        return this.client != null ? SelectionManager.getClipboard(this.client) : "";
    }

    @Override
    protected void init() {
        this.client.keyboard.setRepeatEvents(true);
        this.castButton = this.addDrawableChild(new ButtonWidget(this.width-200 / 2,200,98,20,CAST, (button) -> {
            this.client.setScreen((Screen)null);
            saveSpellSentences(this.Lines);


        }));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        this.setFocused((Element)null);
        RenderSystem.setShaderTexture(0, SLIP_TEXTURE);
        int width = (this.width-150) / 2;
        this.drawTexture(matrices, width, 30, 63, 34, 129, 165,256,256);
        this.Lines = LineAssigner(this.slipWords);
        for(int i = 0; i<Lines.size(); i++)
        {
            int y = i*8;
            this.textRenderer.draw(matrices,Lines.get(i),width+10,50+y,0);
        }



        /*
        CastSlipEditScreen.SlipContent slipContent = this.getSlipContent();
        CastSlipEditScreen.SlipLine[] var15 = slipContent.slipLines;
        int l = var15.length;

        for(int m = 0; m < l; ++m) {
            CastSlipEditScreen.SlipLine line = var15[m];
            this.textRenderer.draw(matrices, line.SlipText, (float)line.x, (float)line.y, -16777216);
        }

         */
        super.render(matrices, mouseX, mouseY, delta);
    }

    private void saveSpellSentences(List<String> placeholder)
    {

        PacketByteBuf bufx = PacketByteBufs.create();
        for(String line: placeholder)
        {
            bufx.writeString(line);
        }
        ClientPlayNetworking.send(this.LINES_PATH,bufx);

    }





    @Override
    public void tick() {
        super.tick();
        tickCounter+=1;

    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if(keyCode == GLFW.GLFW_KEY_ENTER)
        {
            this.slipSelectionManager.insert("\n");
        }

        if(keyCode == GLFW.GLFW_KEY_BACKSPACE)
        {
            this.slipSelectionManager.delete(-1);
        }


        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        World world = player.world;
        if(super.charTyped(chr, modifiers))
        {
            return true;
        }

        else if(this.isValidChar(chr))
        {
            this.slipSelectionManager.insert(Character.toString(chr));
            world.playSound(player,player.getBlockPos(),ModSounds.SCRIBBLE_SOUND_EVENT, SoundCategory.AMBIENT,0.3f,1f);
            return true;
        }


        return false;
    }

    public List<String> LineAssigner(String word) {
        IntList intList = new IntArrayList();
        List<String> placeholder = Lists.newArrayList();
        MutableBoolean mutableBoolean = new MutableBoolean();
        TextHandler textHandler = this.textRenderer.getTextHandler();
            textHandler.wrapLines(word, 114, Style.EMPTY, true, (style, ix, jx) -> {
                String string2 = word.substring(ix, jx);
                mutableBoolean.setValue(string2.endsWith("\n"));
                String string3 = StringUtils.stripEnd(string2, " \n");
                Objects.requireNonNull(this.textRenderer);
                placeholder.add(string3);
                intList.add(ix);
            });

        return placeholder;

    }

    public static boolean isValidChar(char chr) {
        return chr != 167 && chr>= ' ' && chr != 127;
    }


    /*
    private CastSlipEditScreen.SlipContent getSlipContent()
    {
        return this.createSlipContent();
    }

    private CastSlipEditScreen.SlipContent createSlipContent()
    {
        String string = this.getSlipWords();
        if(string.isEmpty())
        {
            return SlipContent.NOTHING;
        }
        else
        {
            int i = this.slipSelectionManager.getSelectionStart();
            int j = this.slipSelectionManager.getSelectionEnd();
            IntList intList = new IntArrayList();
            List<CastSlipEditScreen.SlipLine> list = Lists.newArrayList();
            MutableInt mutableInt = new MutableInt();
            MutableBoolean mutableBoolean = new MutableBoolean();
            TextHandler textHandler = this.textRenderer.getTextHandler();
            textHandler.wrapLines(this.getSlipWords(), 114, Style.EMPTY, true, (style, ix, jx) -> {
                int k = mutableInt.getAndIncrement();
                String string2 = this.getSlipWords().substring(ix, jx);
                mutableBoolean.setValue(string2.endsWith("\n"));
                String string3 = StringUtils.stripEnd(string2, " \n");
                Objects.requireNonNull(this.textRenderer);
                int l = k * 9;
                CastSlipEditScreen.SlipPosition position = this.absolutePositionToScreenPosition(new CastSlipEditScreen.SlipPosition(0, l));
                intList.add(ix);
                list.add(new CastSlipEditScreen.SlipLine(style, string3, position.x, position.y));
            });
            int[] is = intList.toIntArray();
            boolean bl = i == string.length();
            CastSlipEditScreen.SlipPosition position2;
            int m;
            if (bl && mutableBoolean.isTrue()) {
                int var10003 = list.size();
                Objects.requireNonNull(this.textRenderer);
                position2 = new CastSlipEditScreen.SlipPosition(0, var10003 * 9);
            } else {
                int k = getLineFromOffset(is, i);
                m = this.textRenderer.getWidth(string.substring(is[k], i));
                Objects.requireNonNull(this.textRenderer);
                position2 = new CastSlipEditScreen.SlipPosition(m, k * 9);
            }

            List<Rect2i> list2 = Lists.newArrayList();
            if (i != j) {
                m = Math.min(i, j);
                int n = Math.max(i, j);
                int o = getLineFromOffset(is, m);
                int p = getLineFromOffset(is, n);
                int q;
                int t;
                if (o == p) {
                    Objects.requireNonNull(this.textRenderer);
                    q = o * 9;
                    t = is[o];
                    list2.add(this.getLineSelectionRectangle(string, textHandler, m, n, q, t));
                } else {
                    q = o + 1 > is.length ? string.length() : is[o + 1];
                    Objects.requireNonNull(this.textRenderer);
                    list2.add(this.getLineSelectionRectangle(string, textHandler, m, q, o * 9, is[o]));

                    for(t = o + 1; t < p; ++t) {
                        Objects.requireNonNull(this.textRenderer);
                        int u = t * 9;
                        String string2 = string.substring(is[t], is[t + 1]);
                        int v = (int)textHandler.getWidth(string2);
                        CastSlipEditScreen.SlipPosition var10002 = new CastSlipEditScreen.SlipPosition(0, u);
                        Objects.requireNonNull(this.textRenderer);
                        list2.add(this.getRectFromCorners(var10002, new CastSlipEditScreen.SlipPosition(v, u + 9)));
                    }

                    int var10004 = is[p];
                    Objects.requireNonNull(this.textRenderer);
                    list2.add(this.getLineSelectionRectangle(string, textHandler, var10004, n, p * 9, is[p]));
                }
            }
            return new CastSlipEditScreen.SlipContent(string, position2, bl, is, (CastSlipEditScreen.SlipLine[])list.toArray(new CastSlipEditScreen.SlipLine[0]), (Rect2i[])list2.toArray(new Rect2i[0]));
        }

    }

    private Rect2i getLineSelectionRectangle(String string, TextHandler handler, int selectionStart, int selectionEnd, int lineY, int lineStart) {
        String string2 = string.substring(lineStart, selectionStart);
        String string3 = string.substring(lineStart, selectionEnd);
        CastSlipEditScreen.SlipPosition position = new CastSlipEditScreen.SlipPosition((int)handler.getWidth(string2), lineY);
        int var10002 = (int)handler.getWidth(string3);
        Objects.requireNonNull(this.textRenderer);
        CastSlipEditScreen.SlipPosition position2 = new CastSlipEditScreen.SlipPosition(var10002, lineY + 9);
        return this.getRectFromCorners(position, position2);
    }

    private Rect2i getRectFromCorners(CastSlipEditScreen.SlipPosition start, CastSlipEditScreen.SlipPosition end) {
        CastSlipEditScreen.SlipPosition position = this.absolutePositionToScreenPosition(start);
        CastSlipEditScreen.SlipPosition position2 = this.absolutePositionToScreenPosition(end);
        int i = Math.min(position.x, position2.x);
        int j = Math.max(position.x, position2.x);
        int k = Math.min(position.y, position2.y);
        int l = Math.max(position.y, position2.y);
        return new Rect2i(i, k, j - i, l - k);
    }



    static int getLineFromOffset(int[] lineStarts, int position)
    {
        int i = Arrays.binarySearch(lineStarts,position);
        return i < 0 ? -(i+2) : i;
    }

    private CastSlipEditScreen.SlipPosition absolutePositionToScreenPosition(CastSlipEditScreen.SlipPosition position) {
        return new CastSlipEditScreen.SlipPosition(position.x + (this.width - 192) / 2 + 36, position.y + 32);
    }

    private static class SlipContent
    {
        static final CastSlipEditScreen.SlipContent NOTHING;
        private final String slipContent;
        final CastSlipEditScreen.SlipPosition position;
        final boolean atEnd;
        private final int[] lineStarts;
        final CastSlipEditScreen.SlipLine[] slipLines;
        final Rect2i[] selectionRectangles;


        public SlipContent(String slipContent, CastSlipEditScreen.SlipPosition position, boolean atEnd, int[] lineStarts, CastSlipEditScreen.SlipLine[] slipLines, Rect2i[] selectionRectangles)
        {
            this.slipContent = slipContent;
            this.position = position;
            this.atEnd = atEnd;
            this.lineStarts = lineStarts;
            this.slipLines = slipLines;
            this.selectionRectangles = selectionRectangles;
        }

        static
        {
            NOTHING = new CastSlipEditScreen.SlipContent("", new CastSlipEditScreen.SlipPosition(0,0), true, new int[]{0}, new CastSlipEditScreen.SlipLine[]{new CastSlipEditScreen.SlipLine(Style.EMPTY,"",0,0)}, new Rect2i[0]);
        }


    }

    @Environment(EnvType.CLIENT)
    private static class SlipLine
    {
        final Style SlipStyle;
        final String SlipContent;
        final Text SlipText;
        final int x;
        final int y;

        public SlipLine(Style SlipStyle, String SlipContent, int x, int y)
        {
            this.SlipStyle = SlipStyle;
            this.SlipContent = SlipContent;
            this.x = x;
            this.y = y;
            this.SlipText = (new LiteralText(SlipContent)).setStyle(SlipStyle);
        }

    }

    @Environment(EnvType.CLIENT)
    static class SlipPosition {
        public final int x;
        public final int y;

        SlipPosition(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

     */



}
