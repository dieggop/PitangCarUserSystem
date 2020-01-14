package com.desafio.carusersystem.service.impl;

import com.desafio.carusersystem.service.UsuarioService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {
    private BCryptPasswordEncoder bCryptPasswordEncoder;

}
