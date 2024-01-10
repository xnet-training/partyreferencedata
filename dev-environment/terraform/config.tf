resource "vault_generic_secret" "partyreferencedata" {
  path = "secret/partyreferencedata/dev"

  data_json = jsonencode(
    {
      "database"   = {
        "host"     = "172.17.8.220",
        "username" = "microservicio",
        "password" = "secr3t!"
      }
    }
  )
}