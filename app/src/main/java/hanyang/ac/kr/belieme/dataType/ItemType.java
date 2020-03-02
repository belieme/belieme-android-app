package hanyang.ac.kr.belieme.dataType;

import androidx.annotation.Nullable;

public class ItemType {
    public static final int VIEW_TYPE_HEADER = 0;
    public static final int VIEW_TYPE_ITEM = 1;
    private int viewType;

    private int id;
    private String name;
    private String emoji;
    private int count;
    private int amount;

    public ItemType() {

    }

    public ItemType(int id, String name, String emoji, int count, int amount) {
        this.id = id;
        this.name = name;
        this.emoji = emoji;
        this.count = count;
        this.amount = amount;
        this.viewType = VIEW_TYPE_ITEM;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmoji() {
        return emoji;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public String getStatusKorean() {
        if(count <= 0 ) {
            return "대여 불가";
        }
        else {
            return "대여 가능";
        }
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() == this.getClass()) {
            ItemType itemTypeObj = (ItemType)obj;
            if(itemTypeObj.getId() == this.getId() && itemTypeObj.getName().equals(this.getEmoji()) && itemTypeObj.getCount() == this.getCount() && itemTypeObj.getEmoji().equals(this.getEmoji()) && itemTypeObj.getAmount() == this.getAmount()) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    public void increaseCount() {
        if(count < amount) {
            count++;
        }
    }

    public void decreaseCount() {
        if(count > 0) {
            count--;
        }
    }

    public void increaseAmount() { amount++; }

    public void decreasesAmount() {
        if(amount > 0) {
            amount--;
        }
    }
}