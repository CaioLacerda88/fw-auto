#language:pt
@apkinfo
Funcionalidade: Test APK Info

  @tag1
  Cenário: Abrindo atividade principal
    Dado que eu abra a atividade principal
    Quando eu verificar seu carregamento
    Então verifico o texto dos elementos

  @tag2
  Cenário: Abrindo atividade principal e validando
    Dado que eu abra a atividade principal
    Quando eu verificar seu carregamento
    Então verifico o texto do titulo
