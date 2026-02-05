package com.ian.itemservice.web.basic;

import com.ian.itemservice.domain.Item;
import com.ian.itemservice.domain.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);

        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);

        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

    @PostMapping("/add-v1")
    public String addItemV1(
            @RequestParam String name, @RequestParam int price,
            @RequestParam int quantity, Model model
    ) {
        Item item = new Item();
        item.setName(name);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        model.addAttribute("item", item);

        return "basic/item";
    }

    @PostMapping("/add-v2")
    public String addItemV2(@ModelAttribute(name = "item") Item item, Model model) {
        itemRepository.save(item);

        model.addAttribute("item", item);

        return "basic/item";
    }

    /**
     * @ModelAttribute - 요청 파라미터 처리: 객체를 생성하고 요청 파라미터의 값을 프로퍼티 접근법(Setter)으로 입력
     * - Model 추가: ModelAttribute로 지정한 객체를 Model에 자동으로 저장
     */
    @PostMapping("/add-v3")
    public String addItemV3(@ModelAttribute(name = "item") Item item) {
        itemRepository.save(item);

        return "basic/item";
    }

    @PostMapping("/add-v4")
    public String addItemV4(@ModelAttribute Item item) {
        itemRepository.save(item);

        return "basic/item";
    }

    @PostMapping("/add")
    public String addItem(Item item) {
        itemRepository.save(item);

        return "basic/item";
    }

    // 테스트용 더미 데이터
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }
}
