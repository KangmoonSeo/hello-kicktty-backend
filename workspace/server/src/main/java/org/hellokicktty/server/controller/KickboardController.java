package org.hellokicktty.server.controller;

import lombok.RequiredArgsConstructor;
import org.hellokicktty.server.service.KickboardService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KickboardController {

    private final KickboardService kickboardService;


}
