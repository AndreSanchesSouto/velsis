package br.com.velsis.case_tecnico.domain.enums;

/**
 * Define os perfis de acesso (regras/papeis) disponíveis no sistema.
 * Utilizado para restringir ou liberar recursos com base no nível de permissão do usuário.
 */
public enum Role {

    // Perfil padrão: Acesso limitado às funcionalidades básicas e comuns do sistema
    USER,

    // Perfil administrativo: Acesso total ao sistema, incluindo gerenciamento de usuários e configurações
    ADMIN
}