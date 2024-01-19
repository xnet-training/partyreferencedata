pipeline {
  agent any

  stages {
    stage('Check-Out') {
      script {
        git clone https://github.com/xnet-training/partyreferencedata.git
      }
    }
    stage('Construir Imagen de Contenedor') {
      steps {
        script {
          echo "Generando Imagen"
          sh 'docker build . -t partyreferencedata:1.0.2'
        }
      }
    }
  }
}
