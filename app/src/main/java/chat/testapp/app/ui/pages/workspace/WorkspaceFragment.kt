package chat.testapp.app.ui.pages.workspace


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProviders
import chat.testapp.app.databinding.FragmentWorkspaceBinding
import chat.testapp.core.models.Id
import chat.testapp.ui.extentions.getArgument
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein

class WorkspaceFragment : Fragment(), KodeinAware {

    companion object {

        private val WORKSPACE_ID_KEY = "workspace_id_key"

        fun newInstance(workspaceId: Id) = WorkspaceFragment().apply {
            arguments = bundleOf(WORKSPACE_ID_KEY to workspaceId)
        }

    }

    override val kodein: Kodein by closestKodein()

    private lateinit var binding: FragmentWorkspaceBinding

    private val workspaceId by lazy { getArgument(WORKSPACE_ID_KEY, "") }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWorkspaceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setLifecycleOwner(this)
        binding.viewModel = prepareViewModel()
    }

    private fun prepareViewModel(): WorkspaceViewModel {
        val provider = WorkspaceViewModelProvider(kodein, workspaceId)
        return ViewModelProviders.of(this, provider).get(WorkspaceViewModel::class.java)
    }
}
