pipeline {
  agent any

  stages {
    stage('Construir Imagen de Contenedor') {
      steps {
        script {
          sh 'echo "Generando Imagen"'
          docker build . -t partyreferencedata:1.0.2
        }
      }
    }
  }
}
