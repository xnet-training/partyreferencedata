terraform {
  required_providers {
    vault = {
      source = "hashicorp/vault"
      version = "3.21.0"
    }
  }
}

provider "vault" {
    address = "http://172.17.8.220:8201/"
    token   = "root"
}