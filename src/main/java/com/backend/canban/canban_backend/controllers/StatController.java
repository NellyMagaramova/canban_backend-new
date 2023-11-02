package com.backend.canban.canban_backend.controllers;
import com.backend.canban.canban_backend.service.StatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.backend.canban.canban_backend.entity.Stat;
import org.springframework.web.bind.annotation.*;

@RestController
public class StatController {
   private final StatService statService;
   public StatController(StatService statService) {
       this.statService = statService;
   }

   @PostMapping("/stat")
   public ResponseEntity<Stat> findByEmail(@RequestBody String email) {
       return ResponseEntity.ok(statService.findStat(email));
   }
}
