package com.ian.itemservice.web.basic;

import com.ian.itemservice.domain.Item;
import com.ian.itemservice.domain.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

//    @PostMapping("/add-v1")
//    public String addItemV1(
//            @RequestParam String name, @RequestParam int price,
//            @RequestParam int quantity, Model model
//    ) {
//        Item item = new Item();
//        item.setName(name);
//        item.setPrice(price);
//        item.setQuantity(quantity);
//
//        itemRepository.save(item);
//
//        model.addAttribute("item", item);
//
//        return "basic/item";
//    }
//
//    @PostMapping("/add-v2")
//    public String addItemV2(@ModelAttribute(name = "item") Item item, Model model) {
//        itemRepository.save(item);
//
//        model.addAttribute("item", item);
//
//        return "basic/item";
//    }
//
//    /**
//     * @ModelAttribute - 요청 파라미터 처리: 객체를 생성하고 요청 파라미터의 값을 프로퍼티 접근법(Setter)으로 입력
//     * - Model 추가: ModelAttribute로 지정한 객체를 Model에 자동으로 저장
//     */
//    @PostMapping("/add-v3")
//    public String addItemV3(@ModelAttribute(name = "item") Item item) {
//        itemRepository.save(item);
//
//        return "basic/item";
//    }
//
//    @PostMapping("/add-v4")
//    public String addItemV4(@ModelAttribute Item item) {
//        itemRepository.save(item);
//
//        return "basic/item";
//    }
//
//    @PostMapping("/add")
//    public String addItem(Item item) {
//        itemRepository.save(item);
//
//        return "basic/item";
//    }

    /**
     * PRG (Post-Redirect-Get) 패턴 적용
     * POST 요청 처리 후 바로 화면을 반환할 경우, 새로고침 시 동일한 POST 요청이 중복으로 처리될 수 있음
     * 이를 방지하기 위해 POST의 반환을 Redirect로 다른 url로 보낸 후 해당 url에서 GET으로 다시 조회하는 방식으로 화면을 보여줌
     */
//    @PostMapping("/add")
//    public String addItem(Item item) {
//        itemRepository.save(item);
//
//        return "redirect:/basic/items/" + item.getId();
//    }

    /**
     * RedirectAttributes
     * 리다이렉트 시 url 주소에 파라미터 값을 같이 보내는 방식
     * -> return "redirect:/basic/items/" + item.getId(); 와 같이 전송 시 id가 문자열인 경우 인코딩이 깨져서 정상적으로 동작하지 않기 때문
     */
    @PostMapping("/add")
    public String addItem(Item item, RedirectAttributes redirectAttributes) {
        Item saveItem = itemRepository.save(item);

        redirectAttributes.addAttribute("itemId", saveItem.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/basic/items/{itemId}"; // http://localhost:8080/basic/items/3?status=true
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);

        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String editItem(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);

        return "redirect:/basic/items/{itemId}";
    }

    @PostMapping("/{itemId}/delete")
    public String deleteItem(@PathVariable Long itemId) {
        itemRepository.delete(itemId);

        return "redirect:/basic/items";
    }

    // 테스트용 더미 데이터
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }
}
